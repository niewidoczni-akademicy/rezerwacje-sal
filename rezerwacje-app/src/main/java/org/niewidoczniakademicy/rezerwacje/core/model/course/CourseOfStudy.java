package org.niewidoczniakademicy.rezerwacje.core.model.course;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.niewidoczniakademicy.rezerwacje.core.model.Faculty;
import org.niewidoczniakademicy.rezerwacje.core.model.database.User;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CsvBindByName
    @NonNull
    private String name;

    @CsvRecurse
    @NonNull
    @ManyToOne
    private Faculty faculty;

    @CsvBindByName
    @NonNull
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @CsvRecurse
    @NonNull
    private User contactPerson1;    // TODO: does it have to be a User in DB or just anyone?

    @CsvRecurse
    private User contactPerson2;

    @CsvBindByName
    @NonNull
    private boolean isJoined;

    @CsvBindByName
    private String remarks;         // TODO: separate table?

    // TODO: typ egzaminu???
}
