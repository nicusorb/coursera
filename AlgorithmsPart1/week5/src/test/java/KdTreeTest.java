import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class KdTreeTest {
    private KdTree sut = new KdTree();

    @Test(expected = NullPointerException.class)
    public void insertingNullThrowsException() throws Exception {
        sut.insert(null);
    }

    @Test(expected = NullPointerException.class)
    public void containsThrowsExceptionIfArgumentIsNull() throws Exception {
        sut.contains(null);
    }

    @Test
    public void containsWorksCorrectly() throws Exception {
        Point2D p1 = new Point2D(0.5, 0.3);
        Point2D p2 = new Point2D(0.1, 0.2);
        Point2D p3 = new Point2D(0.7, 0.6);
        Point2D p4 = new Point2D(0.01, 0.02);
        Point2D p5 = new Point2D(1, 1);

        sut.insert(p1);
        sut.insert(p2);
        sut.insert(p3);
        sut.insert(p4);
        sut.insert(p5);

        assertTrue(sut.contains(p1));
        assertTrue(sut.contains(p2));
        assertTrue(sut.contains(p3));
        assertTrue(sut.contains(p4));
        assertTrue(sut.contains(p5));
    }

    @Test(expected = NullPointerException.class)
    public void rangeThrowsExceptionIfArgumentIsNull() throws Exception {
        sut.range(null);
    }

    @Test(expected = NullPointerException.class)
    public void nearestThrowsExceptionIfArgumentIsNull() throws Exception {
        sut.nearest(null);
    }
}