package org.niewidoczniakademicy.rezerwacje.model.shared;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StudyType {

    FULL_TIME("FULL_TIME"),
    PART_TIME("PART_TIME");

    private final String code;

    @JsonValue
    public String getCode() {
        return code;
    }
}
