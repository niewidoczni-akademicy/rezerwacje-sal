package org.niewidoczniakademicy.rezerwacje.core.model.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.enums.CourseType;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CsvCourseOfStudy {
    @CsvBindByName(column = "course name", required = true)
    private String name;

    @CsvBindByName(column = "faculty name", required = true)
    private String faculty;

    @CsvCustomBindByName(column = "course type", required = true, converter = CourseTypeConverter.class)
    private CourseType courseType;

    @CsvBindByName(column = "contact login 1", required = true)
    private String contactPerson1Login;

    @CsvBindByName(column = "contact login 2")
    private String contactPerson2Login;

    @CsvBindByName(column = "joined", required = true)
    private Boolean isJoined;

    @CsvBindByName
    private String remarks;         // TODO: separate table?

    // TODO: typ egzaminu???
}
