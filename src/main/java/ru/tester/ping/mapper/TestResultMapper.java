package ru.tester.ping.mapper;

import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.dto.PingResponseDto;

public interface TestResultMapper {
    PingResponseDto convertToDto(TestResult testResult);
    PingResponseDto convertToDto(TestResult testResult, String startDateStrForSearch,
                                 String endDateStrForSearch, String hostForSearch);
}
