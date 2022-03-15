package com.example.route.representative.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    private double x;
    private double y;
    private long epoch;
    private double speed;

}
