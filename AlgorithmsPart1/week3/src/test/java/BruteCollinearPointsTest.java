import org.junit.Test;

/**
 * Created by nicubucalaete on 16/02/16.
 */
public class BruteCollinearPointsTest {
    private BruteCollinearPoints sut;

    @Test(expected = NullPointerException.class)
    public void throwsExceptionIfArgumentIsNull() throws Exception {
        sut = new BruteCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void throwsExceptionIfAnyPointFromTheArrayArgumentIsNull() throws Exception {
        Point[] points = new Point[2];
        points[0] = new Point(1, 2);

        sut = new BruteCollinearPoints(points);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionIfTheArgumentContainsTwoRepeatedPoints() throws Exception {
        Point[] points = new Point[2];
        points[0] = new Point(1, 2);
        points[1] = new Point(1, 2);

        sut = new BruteCollinearPoints(points);
    }
}