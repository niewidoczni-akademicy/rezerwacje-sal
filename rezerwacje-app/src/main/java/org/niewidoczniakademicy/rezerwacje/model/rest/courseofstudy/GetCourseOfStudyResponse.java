package org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCourseOfStudyResponse {

    @NotNull
    private CourseOfStudy courseOfStudy;
}
