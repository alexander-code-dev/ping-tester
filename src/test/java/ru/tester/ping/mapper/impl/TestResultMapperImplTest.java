package ru.tester.ping.mapper.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.dto.PingResponseDto;
import ru.tester.ping.mapper.TestResultMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {TestResultMapperImpl.class})
@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
@DisplayName("Проверка корректности преобразования объектов")
class TestResultMapperImplTest {

    @Autowired
    TestResultMapper testResultMapper;

    @Test
    @DisplayName("Проверка работы преобразование в DTO")
    void convertToDto() {
        TestResult testResult = new TestResult();
        testResult.setHost("8.8.8.8");
        testResult.setId(1L);
        testResult.setExecuteDate(LocalDateTime.now());
        testResult.setDetailMsg("PING 12 (0.0.0.12): 56 data bytes --- 12 ping statistics --- 1 packets transmitted, 0 packets received, 100.0% packet loss");
        testResult.setStatus("ERROR");

        PingResponseDto pingResponseDto = testResultMapper.convertToDto(testResult);

        assertEquals(testResult.getId(), pingResponseDto.getId());
        assertEquals(testResult.getStatus(), pingResponseDto.getStatus());
        assertEquals(testResult.getDetailMsg(), pingResponseDto.getDetailMsg());
        assertEquals(testResult.getHost(), pingResponseDto.getHost());
        assertEquals(testResult.getExecuteDate(), pingResponseDto.getExecuteDate());
    }
}