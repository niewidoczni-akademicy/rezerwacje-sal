package org.niewidoczniakademicy.rezerwacje.model.shared;

public enum CourseType {
    FULL_TIME("full time"),
    EXTERNAL("external");

    private final String typeCode;

    CourseType(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }
}
