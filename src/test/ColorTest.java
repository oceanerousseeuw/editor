package test;

import junit.framework.TestCase;
import org.junit.Test;
import org.ulco.*;
import org.junit.Assert.*;

public class ColorTest extends TestCase {

    @Test
    public void testColor() throws Exception {
        Circle c = new Circle(new Point(0, 0), 10);
        Rectangle r = new Rectangle(new Point(0, 0), 3, 7);
        Square s = new Square(new Point(0, 0), 10);

        assertFalse(r.getM_bgColor().equals(""));
        assertFalse(r.getM_borderColor().equals(""));

        assertFalse(c.getM_bgColor().equals(""));
        assertFalse(c.getM_borderColor().equals(""));

        assertFalse(s.getM_bgColor().equals(""));
        assertFalse(s.getM_borderColor().equals(""));
    }
}