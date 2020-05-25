package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Entity
@Table
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"courseOfStudy", "recruitmentRoom"})
@NoArgsConstructor
@AllArgsConstructor
public class ExamTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @NonNull
    private LocalDate day;

    @Basic
    @NonNull
    private LocalTime timeStart;

    @Basic
    @NonNull
    private LocalTime timeEnd;

    @NonNull
    @ColumnDefault("false")
    private Boolean isDeleted;

    @NonNull
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "recruitment_period_id")
    private RecruitmentPeriod recruitmentPeriod;

    @NonNull
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "course_of_study_id")
    private CourseOfStudy courseOfStudy;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "recruitment_room_id")
    private RecruitmentRoom recruitmentRoom;

    public ExamTerm(final LocalDate day,
                    final LocalTime timeStart,
                    final LocalTime timeEnd,
                    final RecruitmentPeriod recruitmentPeriod,
                    final CourseOfStudy courseOfStudy,
                    final RecruitmentRoom recruitmentRoom) {
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.isDeleted = false;
        this.addRecruitmentPeriod(recruitmentPeriod);
        this.addCourseOfStudy(courseOfStudy);
        this.addRoom(recruitmentRoom);
    }

    public ExamTerm(final LocalDate day,
                    final LocalTime timeStart,
                    final LocalTime timeEnd,
                    final Boolean isDeleted,
                    final RecruitmentPeriod recruitmentPeriod,
                    final CourseOfStudy courseOfStudy,
                    final RecruitmentRoom recruitmentRoom) {
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.isDeleted = isDeleted;
        this.addRecruitmentPeriod(recruitmentPeriod);
        this.addCourseOfStudy(courseOfStudy);
        this.addRoom(recruitmentRoom);
    }

    private void addRecruitmentPeriod(final RecruitmentPeriod recruitmentPeriod) {
        this.recruitmentPeriod = recruitmentPeriod;
    }

    private void addCourseOfStudy(final CourseOfStudy courseOfStudy) {
        this.courseOfStudy = courseOfStudy;
        courseOfStudy.getExamTerms().add(this);
    }

    private void addRoom(final RecruitmentRoom recruitmentRoom) {
        this.recruitmentRoom = recruitmentRoom;
        recruitmentRoom.getExamTerms().add(this);
    }
}
