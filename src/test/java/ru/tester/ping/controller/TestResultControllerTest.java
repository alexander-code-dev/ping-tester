package ru.tester.ping.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.dto.PingResponseDto;
import ru.tester.ping.mapper.TestResultMapper;
import ru.tester.ping.mapper.impl.TestResultMapperImpl;
import ru.tester.ping.sevice.CmdExecutorService;
import ru.tester.ping.sevice.TestResultService;
import ru.tester.ping.sevice.impl.CmdExecutorServiceImpl;
import ru.tester.ping.sevice.impl.TestResultServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = {CmdExecutorServiceImpl.class,
        TestResultServiceImpl.class,
        TestResultMapperImpl.class,
        TestResultController.class
})
@DisplayName("Контроллер для выполнения запросов")
class TestResultControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CmdExecutorService cmdExecutorService;
    @MockBean
    TestResultService testResultService;
    @Autowired
    TestResultMapper testResultMapper;

    TestResult testResult;
    PingResponseDto pingResponseDto;

    @BeforeEach
    void setUp() {
        testResult = new TestResult();
        testResult.setHost("8.8.8.8");
        testResult.setId(1L);
        testResult.setExecuteDate(LocalDateTime.now());
        testResult.setDetailMsg("PING 12 (0.0.0.12): 56 data bytes --- 12 ping statistics --- 1 packets transmitted, 0 packets received, 100.0% packet loss");
        testResult.setStatus("ERROR");

        pingResponseDto = testResultMapper.convertToDto(testResult);
    }

    @Test
    @DisplayName("Проверка получения хоста или адреса для проверки")
    void pingTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/ping").param("host", "8.8.8.8"))
                .andExpect(status().is3xxRedirection());
        Mockito.verify(cmdExecutorService).pingExecutor("8.8.8.8");
    }

    @Test
    @DisplayName("Проверка поиска результатов для отображения 1")
    void searchCheckTest() throws Exception {
        Mockito.when(testResultService
                .findAllByHost("8.8.8.8", "", "", PageRequest.of(0, 5)))
                .thenReturn(new PageImpl<>(List.of(testResult)));

        mockMvc.perform(MockMvcRequestBuilders.get("/searchCheck")
                        .param("host", "8.8.8.8")
                        .param("startDate", "")
                        .param("endDate", "")
                        .param("page", "1")
                        .param("size", "5")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("searchCheckHost"));

        Mockito.verify(testResultService).findAllByHost("8.8.8.8", "", "", PageRequest.of(0, 5));
    }

    @Test
    @DisplayName("Проверка поиска результатов для отображения 2")
    void getDetailsByIdTest() throws Exception {
        Mockito.when(testResultService.getById(testResult.getId())).thenReturn(testResult);
        mockMvc.perform(MockMvcRequestBuilders.get("/details").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("details"))
                .andExpect(model().attribute("pingResponseDtos", pingResponseDto));
    }
}