package ru.tester.ping.sevice;

import ru.tester.ping.dao.entity.TestResult;

public interface CmdExecutorService {
    TestResult pingExecutor(String host);
}
