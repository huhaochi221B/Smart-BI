package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.utils.ExcelUtils;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static com.yupi.springbootinit.common.ChartStatus.*;

@Slf4j
@Component
public class BiMessageConsumer {
    @Resource
    private ChartService chartService;
    @Resource
    private ChatClient genChartClient;


    @SneakyThrows
    @RabbitListener(queues = {BiMqConstant.BI_QUEUE_NAME},ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        if(StringUtils.isBlank(message)){
            //拒绝
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"消息为空");
        }
        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);
        if (chart == null) {
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "图表不存在");
        }
        //先修改任务为执行中，避免重复执行
        Chart updateChart = new Chart();
        updateChart.setId(chart.getId());
        updateChart.setStatus(CHART_STATUS_RUNNING);
        boolean b = chartService.updateById(updateChart);
        if (!b) {
            channel.basicNack(deliveryTag,false,false);
            handleChartUpdateError(chart.getId(), "更新图表执行状态失败");
            return;
        }
        //调用ai
        String result = genChartClient.prompt()
                .user(buildUserInput(chart).toString())
                .call()
                .content();
        String[] split = result.split("【【【【【");
        if (split.length < 3) {
            handleChartUpdateError(chart.getId(), "ai生成错误");
            return;
        }
        String genChart = split[1].trim();
        String genResult = split[2].trim();

        Chart updateChartResult = new Chart();
        updateChartResult.setId(chart.getId());
        updateChartResult.setGenChart(genChart);
        updateChartResult.setGenResult(genResult);
        updateChartResult.setStatus(CHART_STATUS_SUCCEED);
        boolean updateResult = chartService.updateById(updateChartResult);
        if (!updateResult) {
            channel.basicNack(deliveryTag,false,false);
            handleChartUpdateError(chart.getId(), "更新图表成功状态失败");
        }
        //消息确认
        channel.basicAck(deliveryTag,false);

    }

    private String buildUserInput(Chart chart) {
        String goal = chart.getGoal();
        String chartType = chart.getChartType();
        String csvData = chart.getChartData();
        //构造用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("分析需求：").append("\n");
        //拼接分析目标
        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "，使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        userInput.append("分析数据：").append("\n");
        userInput.append(csvData).append("\n");
        return userInput.toString();
    }
    private void handleChartUpdateError(long chartId, String execMessage) {
        Chart updateChartResult = new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setExecMessage(execMessage);
        updateChartResult.setStatus(CHART_STATUS_FAILED);
        boolean updateResult = chartService.updateById(updateChartResult);
        if (!updateResult){
            log.error("更新图表失败状态失败" + chartId +","+ execMessage);
        }
    }
}
