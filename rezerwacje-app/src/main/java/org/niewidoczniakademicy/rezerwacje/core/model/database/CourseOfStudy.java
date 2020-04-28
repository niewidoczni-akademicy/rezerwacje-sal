package org.niewidoczniakademicy.rezerwacje.core.model.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.niewidoczniakademicy.rezerwacje.core.model.enums.CourseType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter @Setter
@ToString
@EqualsAndHashCode(exclude = {"examTerms", "faculty", "contactPerson1", "contactPerson2"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "faculty_id")
    @JsonBackReference
    private Faculty faculty; // TODO Fix creating multiple times the same faculty

    @NonNull
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_person1_id")
    private SystemUser contactPerson1;    // TODO: does it have to be a User in DB or just anyone?

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_person2_id")
    private SystemUser contactPerson2;

    @NonNull
    private Boolean isJoined;

    private String remarks;         // TODO: separate table?

    // TODO: typ egzaminu???

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courseOfStudy")
    private Set<ExamTerm> examTerms = new HashSet<>();

    private void addFacultyTerm(Faculty faculty) {
        faculty.getCourseOfStudies().add(this);
        this.faculty = faculty;
    }

    private void addExamTerm(ExamTerm examTerm) {
        examTerm.setCourseOfStudy(this);
        this.examTerms.add(examTerm);
    }
}
