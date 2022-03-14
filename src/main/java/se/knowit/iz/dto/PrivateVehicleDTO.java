package se.knowit.iz.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PrivateVehicleDTO {

    private String brand;
    private String model;
    private String year;
    private String registrationNumber;
    private int CO2Emissions;
}