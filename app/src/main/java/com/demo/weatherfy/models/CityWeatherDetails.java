package com.demo.weatherfy.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
public class CityWeatherDetails {

    private String cityName;
    private int currentTemp;
    private int currentHumidity;
    private String mainCurrentDescription;
    private String detailedCurrentDescription;
    private int feelsLike;
    private int windSpeed;
    private Boolean exactNameFound;

    public CityWeatherDetails(String cityName, int currentTemp, int currentHumidity, String mainCurrentDescription, String detailedCurrentDescription, int feelsLike, int windSpeed, Boolean exactNameFound) {
        this.cityName = cityName;
        this.currentTemp = currentTemp;
        this.currentHumidity = currentHumidity;
        this.mainCurrentDescription = mainCurrentDescription;
        this.detailedCurrentDescription = detailedCurrentDescription;
        this.feelsLike = feelsLike;
        this.windSpeed = windSpeed;
        this.exactNameFound = exactNameFound;
    }
}

