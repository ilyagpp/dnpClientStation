package com.example.dnpclientstation.domain;

public enum Fuel {
    DIESEL, AI_92, AI_95, SUG;

    public String getFuelType(){
        return name();
    }

}
