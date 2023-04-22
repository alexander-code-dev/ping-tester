package ru.tester.ping.mapper.impl;

import org.springframework.stereotype.Component;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.dto.PingResponseDto;
import ru.tester.ping.mapper.TestResultMapper;

@Component
public class TestResultMapperImpl implements TestResultMapper {

    /**
     * Конвертация в DTO 1
     * @param testResult - сущность бд
     * @return - dto
     */
    public PingResponseDto convertToDto(TestResult testResult) {
        return PingResponseDto.builder()
                .id(testResult.getId())
                .host(testResult.getHost())
                .executeDate(testResult.getExecuteDate())
                .status(testResult.getStatus())
                .detailMsg(testResult.getDetailMsg())
                .build();
    }

    /**
     * Конвертация в DTO 2
     * @param testResult сущность бд
     * @param startDateStrForSearch - дата старта
     * @param endDateStrForSearch - дата окончания
     * @param hostForSearch - запрашиваемый хост
     * @return dto
     */
    public PingResponseDto convertToDto(TestResult testResult, String startDateStrForSearch,
                                        String endDateStrForSearch, String hostForSearch) {
        return PingResponseDto.builder()
                .id(testResult.getId())
                .host(testResult.getHost())
                .executeDate(testResult.getExecuteDate())
                .status(testResult.getStatus())
                .detailMsg(testResult.getDetailMsg())
                .startDateStrForSearch(startDateStrForSearch)
                .endDateStrForSearch(endDateStrForSearch)
                .hostStrForSearch(hostForSearch)
                .build();
    }
}
