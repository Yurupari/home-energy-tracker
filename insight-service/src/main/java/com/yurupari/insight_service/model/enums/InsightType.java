package com.yurupari.insight_service.model.enums;

import lombok.Getter;

@Getter
public enum InsightType {
    SAVING_TIPS("saving-tips"),
    OVERVIEW("overview");

    private final String value;

    InsightType(String value) {
        this.value = value;
    }
}
