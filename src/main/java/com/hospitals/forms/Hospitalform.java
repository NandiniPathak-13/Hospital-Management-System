package com.hospitals.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Hospitalform {
    private String name;
    private String city;
    private String address;
    private String description; // New field for hospital description
    private String location;
    private String phonenumber; // New field for hospital location (coordinates as "lat,long")
    private String openingTime;
    private String closingTime;
}
