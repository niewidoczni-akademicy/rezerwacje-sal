package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruitment_id")
    @JsonBackReference
    private Recruitment recruitment;

    public final void addRecruitment(final Recruitment recruitment) {
        this.recruitment = recruitment;
        recruitment.getRecruitmentPeriods().add(this);
    }
}
