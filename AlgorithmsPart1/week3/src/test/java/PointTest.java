import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointTest {
    @Test
    public void test_compareTo() throws Exception {
        Point p0 = new Point(1, 2);

        assertEquals(-1, p0.compareTo(new Point(3, 4)));
        assertEquals(1, p0.compareTo(new Point(0, 1)));
        assertEquals(-1, p0.compareTo(new Point(1, 3)));
        assertEquals(1, p0.compareTo(new Point(0, 2)));
        assertEquals(0, p0.compareTo(new Point(1, 2)));
    }

    @Test
    public void slopeOfAHorizontalLineSegment_isZero() throws Exception {
        Point p0 = new Point(1, 2);

        assertEquals(+0, p0.slopeTo(new Point(5, 2)), 0.0);
    }

    @Test
    public void slopeOfAVerticalLineSegment_isPositiveInfinity() throws Exception {
        Point p0 = new Point(1, 2);

        assertEquals(Double.POSITIVE_INFINITY, p0.slopeTo(new Point(1, 5)), 0.001);
    }

    @Test
    public void slopeOfADegenerateLineSegment_isNegativeInfinity() throws Exception {
        Point p0 = new Point(1, 2);

        assertEquals(Double.NEGATIVE_INFINITY, p0.slopeTo(new Point(1, 2)), 0.001);
    }

    @Test(expected = NullPointerException.class)
    public void slopeTo_throwException_forNullArgument() throws Exception {
        new Point(0, 0).slopeTo(null);
    }

    @Test
    public void test_SLOPE_ORDER() throws Exception {
        Point p0 = new Point(1, 2);

        assertEquals(0, p0.SLOPE_ORDER.compare(new Point(3, 4), new Point(5, 6)));
        assertEquals(-1, p0.SLOPE_ORDER.compare(new Point(1, 2), new Point(4, 5)));
        assertEquals(-1, p0.SLOPE_ORDER.compare(new Point(3, 4), new Point(4, 6)));
        assertEquals(1, p0.SLOPE_ORDER.compare(new Point(4, 6), new Point(3, 4)));
    }
}
