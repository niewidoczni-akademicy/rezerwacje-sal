package org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditCourseOfStudyRequest {

    private Long id;

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
