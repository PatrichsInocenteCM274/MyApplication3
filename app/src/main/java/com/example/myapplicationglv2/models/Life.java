package com.example.myapplicationglv2.models;

import java.io.Serializable;

public class Life {
    private Boolean isActive;

    public Life(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
