package ru.tester.ping.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tester.ping.dao.entity.TestResult;
import ru.tester.ping.dto.PingRequestDto;
import ru.tester.ping.dto.PingResponseDto;
import ru.tester.ping.mapper.TestResultMapper;
import ru.tester.ping.sevice.CmdExecutorService;
import ru.tester.ping.sevice.TestResultService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestResultController {

    CmdExecutorService cmdExecutorService;
    TestResultService testResultService;
    TestResultMapper testResultMapper;

    /**
     * Получение host | ip для проверки
     * @param pingRequestDto - трансфер in
     * @return - страница
     */
    @PostMapping("/ping")
    public String ping(@ModelAttribute("pingRequestDto") PingRequestDto pingRequestDto
    ) {
        cmdExecutorService.pingExecutor(pingRequestDto.getHost());
        return "redirect:/";
    }

    /**
     * Поиск среди проверок
     * @param host - host | ip для проверки
     * @param startDateStr - начальная дата среза
     * @param endDateStr - конечная дата среза
     * @param page - низ по страницам
     * @param size - верх по страницам
     * @param model - отображение
     * @return - страница
     */
    @GetMapping("/searchCheck")
    public String searchCheck(@RequestParam(value = "host", required = false) String host,
                              @RequestParam(value = "startDate", required = false) String startDateStr,
                              @RequestParam(value = "endDate", required = false) String endDateStr,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              Model model
    ) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<TestResult> testResults = testResultService
                .findAllByHost(host, startDateStr, endDateStr, PageRequest.of(currentPage - 1, pageSize));

        Page<PingResponseDto> pingResponseDtos = testResults
                .map(testResult -> testResultMapper.convertToDto(testResult, startDateStr, endDateStr, host));

        model.addAttribute("pingResponseDtos", pingResponseDtos);
        int totalPages = pingResponseDtos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return host == null ? "searchCheckDate" : "searchCheckHost";
    }

    /**
     * Отображение по идентификатору проверки
     * @param testResultId - id проверки
     * @param model - отображение
     * @return - страница
     */
    @GetMapping("/details")
    public String getDetailsById(@RequestParam("id") Long testResultId, Model model) {
        TestResult testResult = testResultService.getById(testResultId);
        PingResponseDto pingResponseDto = testResultMapper.convertToDto(testResult);
        model.addAttribute("pingResponseDtos", pingResponseDto);
        return "details";
    }

    /**
     * Постраничный детальный вывод
     * @param model - отображение
     * @param page - низ страницы
     * @param size - верх страницы
     * @return - страница
     */
    @GetMapping(value = "/")
    public String getDetailsForMainPage(Model model,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<TestResult> testResults = testResultService.findAllPageable(PageRequest.of(currentPage - 1, pageSize));

        Page<PingResponseDto> pingResponseDtos = testResults.map(testResultMapper::convertToDto);
        model.addAttribute("pingResponseDtos", pingResponseDtos);
        model.addAttribute("pingRequestDto", new PingRequestDto());
        int totalPages = pingResponseDtos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "makeCheck";
    }
}
