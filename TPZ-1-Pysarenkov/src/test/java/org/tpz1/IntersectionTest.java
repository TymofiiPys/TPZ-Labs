package org.tpz1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

class IntersectionTest {

    @Test
    void findIntersectionNullCheck() {
        Line lineNull = null;
        Line lineNotNull1 = Line.createLine(0,0,1,1);
        Line lineNotNull2 = Line.createLine(1,2);

        assertThrows(IllegalArgumentException.class, () -> Intersection.findIntersection(lineNull, lineNotNull1, lineNotNull2));
    }

    @Test
    void createFaultyLines() {
        Line lineNull = null;
        Line lineNotNull1 = Line.createLine(0,0,1,1);
        Line lineNotNull2 = Line.createLine(1,2);

        assertThrows(IllegalArgumentException.class, () -> Line.createLine(1,0));
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(null,null));
        assertThrows(IllegalArgumentException.class, () -> Line.createLine(new Point2D.Double(0,0),new Point2D.Double(0,0)));
    }
}