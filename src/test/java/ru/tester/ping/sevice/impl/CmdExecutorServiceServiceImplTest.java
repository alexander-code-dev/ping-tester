package ru.tester.ping.sevice.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.tester.ping.sevice.CmdExecutorService;

import static org.junit.jupiter.api.Assertions.*;

@Import(CmdExecutorServiceServiceImpl.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest
class CmdExecutorServiceServiceImplTest {

    @Autowired
    CmdExecutorService cmdExecutorService;

    @Test
    void pingExecutor() {
        String host = "e1.ru";
        cmdExecutorService.pingExecutor(host);
    }

}