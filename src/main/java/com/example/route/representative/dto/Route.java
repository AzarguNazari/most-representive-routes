package com.example.route.representative.dto;

import java.util.List;

public record Route(String id, String fromSeq, String toSeq, String fromPort, String toPort, long legDuration, int count, List<Point> linePoints){}


