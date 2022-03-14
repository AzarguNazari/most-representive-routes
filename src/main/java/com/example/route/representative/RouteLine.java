package com.example.route.representative;

import java.util.List;

public class RouteLine{
    private List<List<Double>> linePoint;

    public List<List<Double>> getLinePoint() {
        return linePoint;
    }

    public void setLinePoint(List<List<Double>> linePoint) {
        this.linePoint = linePoint;
    }

    @Override
    public String toString() {
        return "RouteLine{" +
                "linePoint=" + linePoint +
                '}';
    }
}

