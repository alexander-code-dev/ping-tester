package ru.tester.ping.sevice.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.tester.ping.sevice.CmdExecutorService;
import ru.tester.ping.sevice.TestResultRepositoryService;

import java.io.IOException;
import java.io.InputStream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CmdExecutorServiceServiceImpl implements CmdExecutorService {

    TestResultRepositoryService testResultRepositoryService;

    public void pingExecutor(String host) {
        String pingCmd = "ping " + host + " -c 1";

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

            testResultRepositoryService.save(host, inputLine);

            System.out.println(inputLine);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
