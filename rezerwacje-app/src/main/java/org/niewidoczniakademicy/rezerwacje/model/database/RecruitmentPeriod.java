package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.niewidoczniakademicy.rezerwacje.model.shared.StudyDegree;
import org.niewidoczniakademicy.rezerwacje.model.shared.StudyType;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private StudyType studyType;

    @NonNull
    private StudyDegree studyDegree;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruitment_id")
    @JsonManagedReference
    private Recruitment recruitment;

    @Builder.Default
    @ToString.Exclude
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitmentPeriod")
    private Set<ExamTerm> examTerms = new HashSet<>();

    public final void addRecruitment(final Recruitment recruitment) {
        this.recruitment = recruitment;
        recruitment.getRecruitmentPeriods().add(this);
    }
}
