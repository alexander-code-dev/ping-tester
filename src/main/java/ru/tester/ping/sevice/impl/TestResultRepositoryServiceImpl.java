package ru.tester.ping.sevice.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.dao.repository.TestResultRepository;
import ru.tester.ping.enums.Status;
import ru.tester.ping.sevice.TestResultRepositoryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TestResultRepositoryServiceImpl implements TestResultRepositoryService {

    TestResultRepository testResultRepository;
    static String SLICE_STR = "0.0% packet loss";

    @Override
    @Transactional
    public TestResult save(String host, String text) {
        TestResult testResult = new TestResult();
        testResult.setHost(host);
        testResult.setExecuteDate(LocalDateTime.now());
        testResult.setStatus(getStatus(SLICE_STR, text).getIdStatus());
        testResult.setDetailMsg(text);
        return testResultRepository.save(testResult);
    }

    public List<TestResult> findAll() {
        return testResultRepository.findAll();
    }

    @Override
    public Status getStatus(String sliceStr, String fullStr) {
        if (isEmpty(sliceStr) || isEmpty(fullStr)) {
            return Status.ERROR;
        }
        Matcher matcher = Pattern.compile(sliceStr).matcher(fullStr);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return (count == 0) ? Status.ERROR : Status.SUCCESS;
    }
}
