package org.example;

public class Hit {
    public boolean hit(float x, float y, float r) {

        if (x <= 0 && y >= 0) {
            return x >= -r && x <= -r / 2 && y <= r;
        }

        if (x <= 0 && y <= 0) {
            return x >= -r / 2 && y >= -r && y <= x + r / 2;
        }

        if (x >= 0 && y <= 0) {
            return (x * x + y * y) <= (r * r);
        }

        return false;
    }
}