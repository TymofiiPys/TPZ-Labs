package org.tpz1;

import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;

@Getter
@Setter
public class Line {
    private static final double rightBound = 120d;

    private final double A;
    private final double B;
    private final double C;

    private static final double delta = 10e-8;

    private Line() {
        this.A = 0;
        this.B = 0;
        this.C = 0;
    }

    private Line(double A, double B, double C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public static Line createLine(Point2D.Double a, Point2D.Double b) throws IllegalArgumentException {
        // (x - x1)/(x2-x1) = (y-y1)/(y2-y1)
        if (a == null || b == null) {
            throw new IllegalArgumentException("Один із наданих параметрів є null");
        }
        if (!checkBounds(a.x) || !checkBounds(a.y) || !checkBounds(b.x) || !checkBounds(b.y)) {
            throw new IllegalArgumentException(String.format("Введені параметри знаходяться поза межами інтервалу [%.0f; %.0f]", -1 * rightBound, rightBound));
        }
        if (Math.abs(a.x - b.x) < delta) {
            throw new IllegalArgumentException("Введіть такі x, що не співпадають");
        }
        if (Math.abs(a.y - b.y) < delta) {
            throw new IllegalArgumentException("Введіть такі y, що не співпадають");
        }
        if (Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) < delta) {
            throw new IllegalArgumentException("Точки співпадають! Введіть такі точки, що не співпадають");
        }
        double A = b.y - a.y;
        double B = a.x - b.x;
        double C = a.y * b.x - b.y * a.x;
        return new Line(A, B, C);
    }

    public static Line createLine(double x1, double y1, double x2, double y2) {
        return createLine(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
    }

    public static Line createLine(double k, double b) throws IllegalArgumentException {
        // y = kx + b, b != 0 => kx - y - b = 0
        if (!checkBounds(k) || !checkBounds(b)) {
            throw new IllegalArgumentException(String.format("Введені параметри знаходяться поза межами інтервалу [%.0f; %.0f]", -1 * rightBound, rightBound));
        }
        if (Math.abs(b) < delta) {
            throw new IllegalArgumentException("b дорівнює нулю! Введіть b, що не дорівнює нулю");
        }
        return new Line(k, -1, b);
    }

    private static Boolean checkBounds(double a) {
        return Math.abs(a) <= rightBound;
    }
}
