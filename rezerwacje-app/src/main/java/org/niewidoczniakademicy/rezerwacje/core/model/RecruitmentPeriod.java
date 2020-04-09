package org.niewidoczniakademicy.rezerwacje.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Table
@Entity
@NoArgsConstructor
public class RecruitmentPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;
}