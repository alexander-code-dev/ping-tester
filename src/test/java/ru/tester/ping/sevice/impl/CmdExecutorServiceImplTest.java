package ru.tester.ping.sevice.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.sevice.CmdExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {CmdExecutorServiceImpl.class, TestResultServiceImpl.class})
@DataJpaTest
@EnableJpaRepositories("ru.tester.ping.dao.repository")
@EntityScan("ru.tester.ping.dao.entity")
@FieldDefaults(level = AccessLevel.PRIVATE)
@DisplayName("Сервис выполнения запросов")
class CmdExecutorServiceImplTest {

    @Autowired
    CmdExecutorService cmdExecutorService;
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    @DisplayName("Проверка сервиса выполнения запросов к ресурсу")
    void pingExecutor() {
        String host = "e1.ru";
        TestResult testResult = cmdExecutorService.pingExecutor(host);
        TestResult testResultsById = testEntityManager.find(TestResult.class, testResult.getId());
        assertEquals(testResult, testResultsById);
    }
}