package ru.tester.ping.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tester.ping.dao.entity.TestResult;

import java.util.List;

public interface TestResultRepository extends CrudRepository<TestResult, Long> {
    List<TestResult> findAll();
}
