package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"courseOfStudy", "room"})
@NoArgsConstructor
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
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "course_of_study_id")
    private CourseOfStudy courseOfStudy;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "room_id")
    private Room room;

    public ExamTerm(final LocalDate day,
                    final LocalTime timeStart,
                    final LocalTime timeEnd,
                    final CourseOfStudy courseOfStudy,
                    final Room room) {
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.addCourseOfStudy(courseOfStudy);
        this.addRoom(room);
    }

    private void addCourseOfStudy(final CourseOfStudy courseOfStudy) {
        this.courseOfStudy = courseOfStudy;
        courseOfStudy.getExamTerms().add(this);
    }

    private void addRoom(final Room room) {
        this.room = room;
        room.getExamTerms().add(this);
    }
}
