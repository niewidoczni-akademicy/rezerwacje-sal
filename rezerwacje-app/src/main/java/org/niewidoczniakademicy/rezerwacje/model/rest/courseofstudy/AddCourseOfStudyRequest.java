package org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.niewidoczniakademicy.rezerwacje.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.shared.CourseType;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCourseOfStudyRequest {

    private String name;

    @NonNull
    private String faculty;

    @NonNull
    private String courseType;

    @NonNull
    private String contactPerson1;

    private String contactPerson2;

    @NonNull
    private Boolean isJoined;

    private String remarks;
}
