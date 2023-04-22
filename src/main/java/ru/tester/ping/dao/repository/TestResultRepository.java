package ru.tester.ping.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tester.ping.dao.entity.TestResult;

import java.time.LocalDateTime;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    Page<TestResult> findAllByHostOrderByExecuteDateDesc(String host, Pageable pageable);
    Page<TestResult> findAllByExecuteDateBetweenOrderByExecuteDateDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

/*    @Query(value = "select tr from Test_Result tr where 1=1 \n" +
                "    and ( :host is null or tr.host = :host ) \n" +
                "    and ( :startDate is null or tr.execute_Date > :startDate ) \n" +
                "    and ( :endDate is null or tr.execute_Date < :endDate ) \n" +
                "    order by tr.execute_Date desc \n"
            , countQuery = "select count(tr) from TestResult tr where 1=1 \n" +
            "    and ( :host is null or tr.host = :host ) \n" +
            "    and ( :startDate is null or tr.execute_Date > :startDate ) \n" +
            "    and ( :endDate is null or tr.execute_Date < :endDate ) \n"
            , nativeQuery = true)
     Page<TestResult> getTestResult(@Param("host") String host,
                                                    @Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate,
                                                    Pageable pageable);*/
}
