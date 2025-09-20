package org.project.createlearnbe.constant;

import lombok.Getter;

@Getter
public enum Gender {
    FEMALE("Woman", "Nữ"),
    MALE("Man", "Nam"),
    OTHER("Other", "Khác");

    private final String enValue;
    private final String vnValue;

    Gender(String enValue, String vnValue) {
        this.enValue = enValue;
        this.vnValue = vnValue;
    }
}
