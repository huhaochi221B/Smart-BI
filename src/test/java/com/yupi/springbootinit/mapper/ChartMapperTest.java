package com.yupi.springbootinit.mapper;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RequiredArgsConstructor
class ChartMapperTest {

    private final ChartMapper chartMapper;

    @Test
    void getChartData() {
        String querySql = "select * from chart_";
        chartMapper.getChartData(querySql);
    }
}