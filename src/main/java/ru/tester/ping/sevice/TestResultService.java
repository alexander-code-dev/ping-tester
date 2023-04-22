package ru.tester.ping.sevice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.tester.ping.dao.entity.TestResult;

public interface TestResultService {
    TestResult getById(Long id);
    TestResult save(String host, String text);
    Page<TestResult> findAllByHost(String host, String startDate, String endDate, Pageable pageable);
    String getStatus(String fullStr);
    Page<TestResult> findAllPageable(Pageable pageable);
}
