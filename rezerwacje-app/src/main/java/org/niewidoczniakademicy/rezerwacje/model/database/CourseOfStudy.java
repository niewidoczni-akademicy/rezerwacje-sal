package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.univocity.parsers.annotations.EnumOptions;
import com.univocity.parsers.annotations.Nested;
import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.niewidoczniakademicy.rezerwacje.model.shared.CourseType;
import org.niewidoczniakademicy.rezerwacje.service.csv.ContactPersonHeaderTransformer;

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
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"examTerms", "faculty", "contactPerson1", "contactPerson2"})
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Parsed(field = "course name")
    @NonNull
    private String name;

    @Nested
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "faculty_id")
    @JsonBackReference
    private Faculty faculty; // TODO Fix creating multiple times the same faculty

    @Parsed(field = "course type")
    @EnumOptions(customElement = "typeCode")
    @NonNull
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @Nested(headerTransformer = ContactPersonHeaderTransformer.class, args = "c1")
    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_person1_id")
    private Person contactPerson1;    // TODO: does it have to be a User in DB or just anyone?

    @Nested(headerTransformer = ContactPersonHeaderTransformer.class, args = "c2")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_person2_id")
    private Person contactPerson2;

    @Parsed(field = "joined")
    @NonNull
    private Boolean isJoined;

    @Parsed
    private String remarks;         // TODO: separate table?

    // TODO: typ egzaminu???

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
