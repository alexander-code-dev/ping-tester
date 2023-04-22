package ru.tester.ping.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PingResponseDto {
    Long id;
    String host;
    LocalDateTime executeDate;
    String status;
    String detailMsg;

    String startDateStrForSearch;
    String endDateStrForSearch;
    String hostStrForSearch;
}
