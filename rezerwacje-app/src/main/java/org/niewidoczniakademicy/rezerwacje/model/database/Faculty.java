package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter @Setter
@ToString
@EqualsAndHashCode(exclude = {"courseOfStudies"})
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "faculty", cascade = CascadeType.ALL)
    private Set<CourseOfStudy> courseOfStudies = new HashSet<>();

    private void addCourseOfStudy(final CourseOfStudy courseOfStudy) {
        courseOfStudy.setFaculty(this);
        this.courseOfStudies.add(courseOfStudy);
    }
}
