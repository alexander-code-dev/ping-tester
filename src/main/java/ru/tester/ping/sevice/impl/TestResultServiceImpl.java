package ru.tester.ping.sevice.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.dao.repository.TestResultRepository;
import ru.tester.ping.sevice.TestResultService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class TestResultServiceImpl implements TestResultService {

    TestResultRepository testResultRepository;
    static String SLICE_STR1 = "100.0% packet loss";
    static String SLICE_STR2 = "Unknown host";

    /**
     * Запрос по id
     * @param id - id
     * @return - журнал
     */
    @Override
    public TestResult getById(Long id) {
        Optional<TestResult> testResult = testResultRepository.findById(id);
        if (testResult.isPresent()) {
            return testResult.get();
        }
        log.warn("Нет такой статистики, testResultId - {}", id);
        return null;
    }

    /**
     * Сохранение журнала
     * @param host - host | ip
     * @param text - Детали проверки
     * @return - журнал
     */
    @Override
    @Transactional
    public TestResult save(String host, String text) {
        TestResult testResult = new TestResult();
        testResult.setHost(host);
        testResult.setExecuteDate(LocalDateTime.now());
        testResult.setStatus(getStatus(text));
        testResult.setDetailMsg(text);
        return testResultRepository.save(testResult);
    }

    /**
     * Постраничный вывод всех журналов
     * @param pageable - ограничение по журналам
     * @return - пачка журналов
     */
    @Override
    public Page<TestResult> findAllPageable(Pageable pageable) {
        return testResultRepository.findAll(pageable);
    }

    /**
     * Вывод для запрашиваемой статистики
     * @param host - host | ip
     * @param startDateStr - дата от
     * @param endDateStr - дата до
     * @param pageable - количество страниц
     * @return - пачка журналов
     */
    @Override
    public Page<TestResult> findAllByHost(String host, String startDateStr, String endDateStr, Pageable pageable) {
        if (host != null) {
            return testResultRepository.findAllByHostOrderByExecuteDateDesc(host, pageable);
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(startDateStr, dateTimeFormatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, dateTimeFormatter);
            return testResultRepository.findAllByExecuteDateBetweenOrderByExecuteDateDesc(startDate, endDate, pageable);
        }
    }

    /**
     * Вычисление статуса по проверке
     * @param fullStr - строка для проверки
     * @return - результат проверки
     */
    @Override
    public String getStatus(String fullStr) {
        if (fullStr.contains(SLICE_STR1) || fullStr.contains(SLICE_STR2)) {
            log.debug("ERROR");
            return "ERROR";
        } else {
            log.debug("SUCCESS");
            return "SUCCESS";
        }
    }
}
