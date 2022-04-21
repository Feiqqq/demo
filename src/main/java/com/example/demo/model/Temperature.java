package com.example.demo.model;

public final class Temperature {
    private final double value;

    public Temperature(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "value=" + value +
                '}';
    }
}
