package ru.tester.ping.sevice;

import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.enums.Status;

public interface TestResultRepositoryService {
    TestResult save(String host, String text);
    Status getStatus(String sliceStr, String fullStr);
}
