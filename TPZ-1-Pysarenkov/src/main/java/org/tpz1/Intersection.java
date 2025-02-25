package org.tpz1;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Objects;

public class Intersection {
    private static final double delta = 1e-8;

    public static String findIntersection(Line line1, Line line2, Line line3) {
        if (line1 == null || line2 == null || line3 == null) {
            throw new IllegalArgumentException("Один із наданих параметрів є null");
        }
        final IntersectionPoint i12 = IntersectionPoint.getIntersectionPoint(line1, line2);
        final IntersectionPoint i13 = IntersectionPoint.getIntersectionPoint(line1, line3);
        final IntersectionPoint i23 = IntersectionPoint.getIntersectionPoint(line2, line3);

        List<IntersectionPoint> intersectionPoints = List.of(i12, i13, i23);

        if (intersectionPoints.stream().allMatch(x -> x.type.equals("COINCIDE"))) {
            return "Прямі співпадають";
        }
        // Прямі або перетинаються в двох точках, або не перетинаються (три паралельні або дві співпадають)
        if (intersectionPoints.stream().anyMatch(x -> x.type.equals("PARALLEL"))) {
            // Наприклад, прямі 1 і 2 паралельні. Пряма 3 або паралельна 1 і 2, або співпадає з одною з них
            if (intersectionPoints.stream().filter(x -> x.type.equals("PARALLEL")).count() >= 2) {
                return "Прямі не перетинаються";
            } else {
                // Прямі 1 і 2 паралельні, пряма 3 їх перетинає
                List<Point2D.Double> inters = intersectionPoints.stream().
                        filter(x -> !x.type.equals("PARALLEL")).
                        map(x -> x.point).
                        toList();

                return String.format("Прямі перетинаються у двох точках: (%.3f, %.3f), (%.3f, %.3f).",
                        inters.get(0).x, inters.get(0).y,
                        inters.get(1).x, inters.get(1).y);
            }
        }
        List<Point2D.Double> ipNotNull = intersectionPoints.stream().map(x -> x.point).filter(Objects::nonNull).distinct().toList();
        if (ipNotNull.size() == 1) {
            Point2D.Double inters = ipNotNull.get(0);
            return String.format("Прямі перетинаються в одній точці: (%.3f, %.3f).",
                    inters.x, inters.y);
        }
        return String.format("Прямі перетинаються у трьох точках: (%.3f, %.3f), (%.3f, %.3f), (%.3f, %.3f).",
                i12.point.x, i12.point.y,
                i13.point.x, i13.point.y,
                i23.point.x, i23.point.y);
    }

    private static class IntersectionPoint {
        public String type;
        public Point2D.Double point;

        public IntersectionPoint(String type, Point2D.Double point) {
            this.type = type;
            this.point = point;
        }

        private static IntersectionPoint getIntersectionPoint(Line line1, Line line2) {
            final double A1 = line1.getA();
            final double B1 = line1.getB();
            final double C1 = line1.getC();

            final double A2 = line2.getA();
            final double B2 = line2.getB();
            final double C2 = line2.getC();

            if (Math.abs(A1 * B2 - A2 * B1) < delta) {
                if (Math.abs(A1 * C2 - C1 * A2) < delta && Math.abs(B1 * C2 - C1 * B2) < delta) {
                    // Прямі співпадають
                    return new IntersectionPoint("COINCIDE", null);
                } else {
                    // Прямі паралельні
                    return new IntersectionPoint("PARALLEL", null);
                }
            }

            if (A1 == 0) {  // First line is horizontal
                double y = -C1 / B1;
                double x = -(B2 * y + C2) / A2;
                return new IntersectionPoint("", new Point2D.Double(x, y));
            }
            if (B1 == 0) {  // First line is vertical
                double x = -C1 / A1;
                double y = -(A2 * x + C2) / B2;
                return new IntersectionPoint("", new Point2D.Double(x, y));
            }
            if (A2 == 0) {  // Second line is horizontal
                double y = -C2 / B2;
                double x = -(B1 * y + C1) / A1;
                return new IntersectionPoint("", new Point2D.Double(x, y));
            }
            if (B2 == 0) {  // Second line is vertical
                double x = -C2 / A2;
                double y = -(A1 * x + C1) / B1;
                return new IntersectionPoint("", new Point2D.Double(x, y));
            }

            // Загальний випадок
            double x = -(C1 * B2 - C2 * B1) / (A1 * B2 - A2 * B1);
            double y = -(A1 * C2 - A2 * C1) / (A1 * B2 - A2 * B1);

            return new IntersectionPoint("", new Point2D.Double(x, y));
        }
    }
}
