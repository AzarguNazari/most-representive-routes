package com.example.route.representative.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route{

    private String id;
    private String fromSeq;
    private String toSeq;
    private String fromPort;
    private String toPort;
    private long legDuration;
    private int count;
    private List<Point> linePoints;

}


