package org.niewidoczniakademicy.rezerwacje.core.model.csv;

import com.univocity.parsers.annotations.Parsed;
import lombok.*;
import org.springframework.lang.NonNull;

@Getter @Setter
@ToString
@NoArgsConstructor
public class Room {
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
