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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"course_of_study_id", "user_id"})
})
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "course_of_study_id")
    private CourseOfStudy courseOfStudy;

    @NonNull
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    private SystemUser systemUser;

    public final void addCourseOfStudy(final CourseOfStudy courseOfStudy) {
        this.courseOfStudy = courseOfStudy;
        this.courseOfStudy.getUserCourses().add(this);
    }

    public final void addSystemUser(final SystemUser systemUser) {
        this.systemUser = systemUser;
        this.systemUser.getUserCourses().add(this);
    }
}
