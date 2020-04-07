package org.niewidoczniakademicy.rezerwacje.core.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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
