package org.tpz1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.List;

class IntersectionTest {
    //Неприпустимі класи

    @Test
    void findIntersectionNullCheck() {
        Line lineNull = null;
        Line lineNotNull1 = Line.createLine(0, 0, 1, 1);
        Line lineNotNull2 = Line.createLine(1, 2);

        assertThrows(IllegalArgumentException.class, () -> Intersection.findIntersection(lineNull, lineNotNull1, lineNotNull2));
    }

    @Test
    void createFaultyLines() {
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(1, 0));
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(null, null));

        // Ліва границя
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(new Point2D.Double(-120, -120), new Point2D.Double(-120, -120)));

        // Права границя
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(new Point2D.Double(120, 120), new Point2D.Double(120, 120)));

        // Середина
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(new Point2D.Double(0, 0), new Point2D.Double(0, 0)));

    }

    @Test
    void outOfBoundsParameters() {
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(121, 121));
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(121, 121, -121, -121));
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(0, 121, 0, 0));

        // Ліва границя
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(new Point2D.Double(-121, -121), new Point2D.Double(-121, -122)));

        // Права границя
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(new Point2D.Double(121, 121), new Point2D.Double(121, 122)));
    }

    // Припустимі класи

    @Test
    void linesCoincide() {
        // Ліва границя
        Line line1 = Line.createLine(0, -120, -120, 0);
        Line line2 = Line.createLine(0, -120, -120, 0);
        Line line3 = Line.createLine(-1, -120);

        assertEquals("Прямі співпадають", Intersection.findIntersection(line1, line2, line3));

        // Права границя
        line1 = Line.createLine(0, 120, 120, 0);
        line2 = Line.createLine(0, 120, 120, 0);
        line3 = Line.createLine(-1, 120);

        assertEquals("Прямі співпадають", Intersection.findIntersection(line1, line2, line3));

        //Середина
        line1 = Line.createLine(1, 0, 5, 4);
        line2 = Line.createLine(1, 0, 5, 4);
        line3 = Line.createLine(1, -1);

        assertEquals("Прямі співпадають", Intersection.findIntersection(line1, line2, line3));

        // Перша - ліва, друга - середина, третя - права
        line1 = Line.createLine(-120, -119, -119, -118);
        line2 = Line.createLine(119, 120, 118, 119);
        line3 = Line.createLine(1, 1);

        assertEquals("Прямі співпадають", Intersection.findIntersection(line1, line2, line3));

        // дві - ближчі до лівої, третя - середина
        line1 = Line.createLine(-120, -119, -119, -118);
        line2 = Line.createLine(-120, -119, -119, -118);
        line3 = Line.createLine(1, 1);

        assertEquals("Прямі співпадають", Intersection.findIntersection(line1, line2, line3));

        // Дві - ближче до правої, третя - середина
        line1 = Line.createLine(119, 120, 118, 119);
        line2 = Line.createLine(119, 120, 118, 119);
        line3 = Line.createLine(1, 1);

        assertEquals("Прямі співпадають", Intersection.findIntersection(line1, line2, line3));
    }

    @Test
    void linesParallel() {
        // Ліва границя
        Line line1 = Line.createLine(-120, -120, -119, -119);
        Line line2 = Line.createLine(-120, -119, -119, -118);
        Line line3 = Line.createLine(1, -120);

        assertEquals("Прямі не перетинаються", Intersection.findIntersection(line1, line2, line3));

        //Права границя

        line1 = Line.createLine(120, 120, 119, 119);
        line2 = Line.createLine(119, 120, 118, 119);
        line3 = Line.createLine(1, 120);

        assertEquals("Прямі не перетинаються", Intersection.findIntersection(line1, line2, line3));

        //Середина
        line1 = Line.createLine(1, 0, 5, 4);
        line2 = Line.createLine(1, 0, 5, 4);
        line3 = Line.createLine(1, -2);

        assertEquals("Прямі не перетинаються", Intersection.findIntersection(line1, line2, line3));

        // Перша - ліва, друга - середина, третя - права
        line1 = Line.createLine(-120, -119, -119, -118);
        line2 = Line.createLine(120, 120, 119, 119);
        line3 = Line.createLine(1, 1);

        assertEquals("Прямі не перетинаються", Intersection.findIntersection(line1, line2, line3));

        // дві - ближчі до лівої, третя - середина
        line1 = Line.createLine(-120, -119, -119, -118);
        line2 = Line.createLine(-120, -120, -119, -119);
        line3 = Line.createLine(1, -1);

        assertEquals("Прямі не перетинаються", Intersection.findIntersection(line1, line2, line3));

        // Дві - ближче до правої, третя - середина
        line1 = Line.createLine(120, 119, 119, 118);
        line2 = Line.createLine(120, 120, 119, 119);
        line3 = Line.createLine(1, 1);

        assertEquals("Прямі не перетинаються", Intersection.findIntersection(line1, line2, line3));
    }

    @Test
    void linesIntersectSingle() {
        // Ліва границя
        Line line1 = Line.createLine(-120, -120, -119, -119);
        Line line2 = Line.createLine(-120, -120, -119, -118);
        Line line3 = Line.createLine(0, -120);
        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються в одній точці: "));

        // Права границя
        line1 = Line.createLine(120, 120, 119, 119);
        line2 = Line.createLine(120, 120, 119, 118);
        line3 = Line.createLine(0, 120);
        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються в одній точці: "));

        //Середина
        line1 = Line.createLine(7, 0, 5, 4);
        line2 = Line.createLine(1, 0, 5, 4);
        line3 = Line.createLine(1, -1);

        assertEquals("Прямі перетинаються в одній точці: (5.000, 4.000).", Intersection.findIntersection(line1, line2, line3));

        // Перша - ліва, друга - середина, третя - права
        line1 = Line.createLine(-119, -120, -118, -119);
        line2 = Line.createLine(120, 120, 119, 118);
        line3 = Line.createLine(1, -1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються в одній точці: "));

        // дві - ближчі до лівої, третя - середина
        line1 = Line.createLine(-119, -120, -118, -119);
        line2 = Line.createLine(-120, -120, -119, -118);
        line3 = Line.createLine(1, -1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються в одній точці: "));

        // Дві - ближче до правої, третя - середина
        line1 = Line.createLine(120, 119, 119, 118);
        line2 = Line.createLine(120, 120, 119, 118);
        line3 = Line.createLine(1, -1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються в одній точці: "));
    }

    @Test
    void linesIntersectDouble() {
        // середина
        Line lineA = Line.createLine(7, 0, 5, 4);
        Line lineB = Line.createLine(1, 0, 5, 4);
        Line lineC = Line.createLine(1, -2);

        assertTrue(
                List.of("Прямі перетинаються у двох точках:", "(5.000, 4.000)", "(5.333, 3.333)")
                        .stream()
                        .allMatch(x -> Intersection.findIntersection(lineA, lineB, lineC).contains(x))
        );

        // Ліва границя
        Line line1 = Line.createLine(-120, -119, -119, -118);
        Line line2 = Line.createLine(-120, -120, -119, -119);
        Line line3 = Line.createLine(0, -120);
        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у двох точках: "));

        // права границя
        line1 = Line.createLine(120, 119, 119, 118);
        line2 = Line.createLine(120, 120, 119, 119);
        line3 = Line.createLine(0, 120);
        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у двох точках: "));

        // Перша - ліва, друга - середина, третя - права
        line1 = Line.createLine(-120, -119, -119, -120);
        line2 = Line.createLine(120, 120, 119, 119);
        line3 = Line.createLine(1, 1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у двох точках: "));

        // дві - ближчі до лівої, третя - середина
        line1 = Line.createLine(-120, -119, -119, -120);
        line2 = Line.createLine(-120, -120, -119, -119);
        line3 = Line.createLine(1, 1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у двох точках: "));

        // Дві - ближче до правої, третя - середина
        line1 = Line.createLine(120, 119, 119, 120);
        line2 = Line.createLine(120, 120, 119, 119);
        line3 = Line.createLine(1, 1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у двох точках: "));
    }

    @Test
    void linesIntersectTriple() {
        //Середина
        Line lineA = Line.createLine(7, 0, 5, 4);
        Line lineB = Line.createLine(1, 0, 5, 4);
        Line lineC = Line.createLine(-1, 4);

        assertTrue(
                List.of("Прямі перетинаються у трьох точках:", "(5.000, 4.000)", "(10.000, -6.000)", "(2.500, 1.500)")
                        .stream()
                        .allMatch(x -> Intersection.findIntersection(lineA, lineB, lineC).contains(x))
        );

        // Ліва границя
        Line line1 = Line.createLine(-120, -119, -119, -120);
        Line line2 = Line.createLine(-120, -120, -119, -119);
        Line line3 = Line.createLine(-120, -120);
        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у трьох точках: "));

        // права границя
        line1 = Line.createLine(120, 119, 119, 120);
        line2 = Line.createLine(120, 120, 119, 119);
        line3 = Line.createLine(120, 120);
        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у трьох точках: "));

        // Перша - ліва, друга - середина, третя - права
        line1 = Line.createLine(-120, -119, -119, -120);
        line2 = Line.createLine(120, 120, 119, 119);
        line3 = Line.createLine(2, 1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у трьох точках: "));

        // дві - ближчі до лівої, третя - середина
        line1 = Line.createLine(-120, -119, -119, -120);
        line2 = Line.createLine(-120, -120, -119, -119);
        line3 = Line.createLine(2, 1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у трьох точках: "));

        // Дві - ближче до правої, третя - середина
        line1 = Line.createLine(120, 119, 119, 120);
        line2 = Line.createLine(120, 120, 119, 119);
        line3 = Line.createLine(2, 1);

        assertTrue(Intersection.findIntersection(line1, line2, line3).startsWith("Прямі перетинаються у трьох точках: "));
    }
}