package org.niewidoczniakademicy.rezerwacje.core.model.csv;

import com.univocity.parsers.annotations.EnumOptions;
import com.univocity.parsers.annotations.Nested;
import com.univocity.parsers.annotations.Parsed;
import lombok.*;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.core.model.enums.CourseType;


@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfStudy {
    @Parsed(field = "course name")
    @NonNull
    private String name;

    @Nested
    @NonNull
    private Faculty faculty;

    @Parsed(field = "course type")
    @EnumOptions(customElement = "typeCode")
    @NonNull
    private CourseType courseType;

    @NonNull
    private String contactPerson1Login;

    private String contactPerson2Login;

    @Parsed(field = "joined")
    @NonNull
    private Boolean isJoined;

    @Parsed
    private String remarks;         // TODO: separate table?

    // TODO: typ egzaminu???
}
