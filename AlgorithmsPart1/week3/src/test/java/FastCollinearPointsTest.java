import org.junit.Test;

public class FastCollinearPointsTest {
    private FastCollinearPoints sut;
    
    @Test(expected = NullPointerException.class)
    public void throwsExceptionIfArgumentIsNull() throws Exception {
        sut = new FastCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void throwsExceptionIfAnyPointFromTheArrayArgumentIsNull() throws Exception {
        Point[] points = new Point[2];
        points[0] = new Point(1, 2);

        sut = new FastCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionIfTheArgumentContainsTwoRepeatedPoints() throws Exception {
        Point[] points = new Point[2];
        points[0] = new Point(1, 2);
        points[1] = new Point(1, 2);

        sut = new FastCollinearPoints(points);
    }
}