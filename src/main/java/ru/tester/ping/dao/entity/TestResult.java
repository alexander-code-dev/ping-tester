package ru.tester.ping.dao.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TEST_RESULT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEST_RESULT_SEQ")
    @SequenceGenerator(name = "TEST_RESULT_SEQ", sequenceName = "TEST_RESULT_ID_SEQ", allocationSize = 1)
    Long id;

    String host;
    LocalDateTime executeDate;
    Integer status;
    String detailMsg;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestResult that = (TestResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", execute_date=" + executeDate +
                ", status=" + status +
                ", detailMsg='" + detailMsg + '\'' +
                '}';
    }
}
