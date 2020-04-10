package org.niewidoczniakademicy.rezerwacje.core.model.database;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"building", "name"})
})
@Data
@NoArgsConstructor
public class Room {  // TODO: how to model this?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CsvBindByName
    @NonNull
    private String building;

    @CsvBindByName
    @NonNull
    private String name;
    
    @CsvBindByName
    @NonNull
    private Integer capacity;
}
