package com.yupi.springbootinit.mapper;

import com.yupi.springbootinit.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
* @author huhao
* @description 针对表【chart(图表信息表)】的数据库操作Mapper
* @createDate 2026-04-11 11:27:26
* @Entity com.yupi.springbootinit.model.entity.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {
    Map<String, Object> getChartData(String querySql);

}




