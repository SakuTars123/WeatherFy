package com.demo.weatherfy.models;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
public class Location {

    private int latitude;
    private int longitude;
    private String name;

    public Location(int latitude, int longitude, String name, boolean exactLocationFound) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.exactLocationFound = exactLocationFound;
    }

    private boolean exactLocationFound;


}
