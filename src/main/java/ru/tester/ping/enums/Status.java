package ru.tester.ping.enums;

import lombok.Getter;

@Getter
public enum Status {
    SUCCESS(1),
    ERROR(0);

    private final int idStatus;

    Status(int idStatus) {
        this.idStatus = idStatus;
    }
}
