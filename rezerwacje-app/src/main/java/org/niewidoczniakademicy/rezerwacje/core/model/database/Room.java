package org.niewidoczniakademicy.rezerwacje.core.model.database;

import com.univocity.parsers.annotations.Parsed;
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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Parsed
    @NonNull
    private String building;

    @Parsed
    @NonNull
    private String name;

    @Parsed
    @NonNull
    private Integer capacity;
}
