package org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCoursesOfStudiesForUserResponse {

    private List<Long> coursesOfStudiesIdsForUser;
}
