package com.medizine.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ModuleType {

    USER, DOCTOR;

    @JsonCreator
    public static ModuleType fromString(String value) {
        if (value != null) {
            return ModuleType.valueOf(Utility.enumConverter(value));
        }
        return null;
    }
}
