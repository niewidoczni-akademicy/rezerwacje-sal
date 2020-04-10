package org.niewidoczniakademicy.rezerwacje.core.model.course;

public enum CourseType {
    FULL_TIME("full time"),
    EXTERNAL("external");

    public final String typeCode;

    CourseType(String typeCode) {
        this.typeCode = typeCode;
    }
}
