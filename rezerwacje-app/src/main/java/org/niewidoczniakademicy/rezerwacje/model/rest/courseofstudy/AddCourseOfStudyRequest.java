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
public class AddCourseOfStudyRequest {

    private String name;

    @NonNull
    private Long facultyId;

    @NonNull
    private String courseType;

    @NonNull
    private Long contactPerson1Id;

    private Long contactPerson2Id;

    @NonNull
    private Boolean isJoined;

    private String remarks;
}
