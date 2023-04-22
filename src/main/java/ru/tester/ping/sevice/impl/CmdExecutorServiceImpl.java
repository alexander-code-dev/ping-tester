package ru.tester.ping.sevice.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.sevice.CmdExecutorService;
import ru.tester.ping.sevice.TestResultService;

import java.io.IOException;
import java.io.InputStream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CmdExecutorServiceImpl implements CmdExecutorService {

    TestResultService testResultService;

    /**
     * Выполнение запроса по host | ip
     * @param host - host | ip
     * @return - результат проверки
     */
    public TestResult pingExecutor(String host) {
        String pingCmd = "ping " + host + " -c 1 -W 1";

        try {
            String inputLine;

            Runtime r = Runtime.getRuntime();
            Process p = r.exec(pingCmd);

            try (InputStream inputStream = p.getInputStream();
                 InputStream errorStream = p.getErrorStream()) {
                inputLine = new String(inputStream.readAllBytes());
                if (inputLine.isEmpty()) {
                    inputLine = new String(errorStream.readAllBytes());
                }
            }
            return testResultService.save(host, inputLine);

        } catch (IOException e) {
            log.error("Произошла ошибка в выполнении команды ping.", e);
            return null;
        }
    }
}
