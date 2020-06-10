package org.niewidoczniakademicy.rezerwacje.model.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayOfWeek {

    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    private final String code;

    @JsonCreator
    public static DayOfWeek fromCode(String code) {
        if (code == null)
            return null;

        code = code.toLowerCase();
        for (DayOfWeek dayOfWeek : values()) {
            if (dayOfWeek.code.equals(code)) {
                return dayOfWeek;
            }
        }
        throw new IllegalArgumentException("Invalid string code passed: " + code);
    }
}