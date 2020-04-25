package org.niewidoczniakademicy.rezerwacje.core.model.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Parsed(field = "faculty name")
    @NonNull
    private String name;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "faculty", cascade = CascadeType.ALL)
    private Set<CourseOfStudy> courseOfStudies = new HashSet<>();

    private void addCourseOfStudy(CourseOfStudy courseOfStudy) {
        courseOfStudy.setFaculty(this);
        this.courseOfStudies.add(courseOfStudy);
    }
}
