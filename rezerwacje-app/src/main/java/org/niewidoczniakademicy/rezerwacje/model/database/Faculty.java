package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(exclude = {"courseOfStudies"})
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @OneToOne
    @ToString.Exclude
    @JsonIgnore
    private Faculty predecessor;

    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;

    @ToString.Exclude
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "faculty", cascade = CascadeType.ALL)
    private Set<CourseOfStudy> courseOfStudies;

    private void addCourseOfStudy(final CourseOfStudy courseOfStudy) {
        courseOfStudy.setFaculty(this);
        this.courseOfStudies.add(courseOfStudy);
    }
}
