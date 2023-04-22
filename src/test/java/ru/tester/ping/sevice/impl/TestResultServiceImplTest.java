package ru.tester.ping.sevice.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.sevice.TestResultService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestResultServiceImpl.class)
@DataJpaTest
@DisplayName("Проверка слоя работы с бд")
class TestResultServiceImplTest {

    @Autowired
    TestResultService testResultService;
    @Autowired
    TestEntityManager testEntityManager;

    static String FULL_STR = "PING 8.8.8.8 (8.8.8.8): 56 data bytes\n" +
            "64 bytes from 8.8.8.8: icmp_seq=0 ttl=107 time=216.825 ms\n" +
            "\n" +
            "--- 8.8.8.8 ping statistics ---\n" +
            "1 packets transmitted, 1 packets received, 0.0% packet loss\n" +
            "round-trip min/avg/max/stddev = 216.825/216.825/216.825/0.000 ms";

    @Test
    @DisplayName("Проверка сохранения в бд")
    void saveTest() {
        TestResult testResult = testResultService.save("8.8.8.8", FULL_STR);
        TestResult testResultsById = testEntityManager.find(TestResult.class, testResult.getId());
        assertEquals(testResult, testResultsById);
    }

    @Test
    @DisplayName("Проверка определения статуса по ответу на выполненный запрос")
    void getStatusTest() {
        assertEquals(testResultService.getStatus(FULL_STR), "SUCCESS");
    }
}