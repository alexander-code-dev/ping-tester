package ru.tester.ping.sevice.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.sevice.TestResultRepositoryService;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestResultRepositoryServiceImpl.class)
@DataJpaTest
class TestResultRepositoryServiceImplTest {

    @Autowired
    TestResultRepositoryService testResultRepositoryService;
    @Autowired
    TestEntityManager testEntityManager;

    static String FULL_STR = "PING 8.8.8.8 (8.8.8.8): 56 data bytes\n" +
            "64 bytes from 8.8.8.8: icmp_seq=0 ttl=107 time=216.825 ms\n" +
            "\n" +
            "--- 8.8.8.8 ping statistics ---\n" +
            "1 packets transmitted, 1 packets received, 0.0% packet loss\n" +
            "round-trip min/avg/max/stddev = 216.825/216.825/216.825/0.000 ms";

    @Test
    void saveTest() {
        TestResult testResult = testResultRepositoryService.save("8.8.8.8", FULL_STR);
        TestResult testResultsById = testEntityManager.find(TestResult.class, testResult.getId());
        assertEquals(testResult, testResultsById);
    }

    @Test
    void getStatusTest() {
        String sliceStr = "0.0% packet loss";
        assertEquals(testResultRepositoryService.getStatus(sliceStr, FULL_STR).getIdStatus(), 1);
    }
}