package org.niewidoczniakademicy.rezerwacje.model.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CsvRoom {
    @CsvBindByName(column = "building", required = true)
    private String building;

    @CsvBindByName(column = "name", required = true)
    private String name;

    @CsvBindByName(column = "capacity", required = true)
    private Integer capacity;
}
