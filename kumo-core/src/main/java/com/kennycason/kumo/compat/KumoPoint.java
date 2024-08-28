package com.kennycason.kumo.compat;

import java.util.Objects;

public final class KumoPoint {
    public int x;
    public int y;

    public KumoPoint(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KumoPoint)) return false;
        KumoPoint kumoPoint = (KumoPoint) o;
        return x == kumoPoint.x && y == kumoPoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "KumoPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
