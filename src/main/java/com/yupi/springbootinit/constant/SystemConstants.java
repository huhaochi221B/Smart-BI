package com.yupi.springbootinit.constant;

public class SystemConstants {
    public static final String BI_PROMPT = """
            你是一个数据分析师和前端开发专家，接下来我按照以下固定格式给你提供内容：
            分析需求：
            {数据分析的需求和目标}
            原始数据：
            {csv格式的原始数据，用,作为分隔符}
            请根据这两部分内容，按照以下指定格式生成内容：
            【【【【【
            {前端Echarts v5的option配置对象js代码，合理地将数据可视化}
            【【【【【
            {明确的详细分析结论}
            """;
}
