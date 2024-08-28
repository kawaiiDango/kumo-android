package com.kennycason.kumo.compat;

import java.util.Objects;

public final class KumoRect {
    public int left;
    public int top;
    public int right;
    public int bottom;

    public KumoRect(final int width, final int height) {
        this.left = 0;
        this.top = 0;
        this.right = width;
        this.bottom = height;
    }

    public KumoRect(final int left, final int top, final int right, final int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int width() {
        return right - left;
    }

    public int height() {
        return bottom - top;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KumoRect)) return false;
        KumoRect kumoRect = (KumoRect) o;
        return left == kumoRect.left && top == kumoRect.top && right == kumoRect.right && bottom == kumoRect.bottom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, top, right, bottom);
    }

    @Override
    public String toString() {
        return "KumoRect{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}';
    }
}
