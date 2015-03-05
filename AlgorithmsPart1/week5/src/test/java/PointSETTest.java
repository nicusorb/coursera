import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PointSETTest {
    private final PointSET sut = new PointSET();

    @Test
    public void isEmpty_returnTrueIfNoPointsWereAdded() throws Exception {
        assertThat(sut.isEmpty(), is(true));
    }

    @Test
    public void isEmpty_returnsFalseIfPointsWereAdded() throws Exception {
        sut.insert(new Point2D(1, 1));

        assertThat(sut.isEmpty(), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void insertThrowException_ifInsertingNull() throws Exception {
        sut.insert(null);
    }

    @Test
    public void size_returnsZeroIfNoPointsWereAdded() throws Exception {
        assertThat(sut.size(), is(0));
    }

    @Test
    public void size_returnsNumberOfPointsInserted() throws Exception {
        sut.insert(new Point2D(1, 1));

        assertThat(sut.size(), is(1));
    }
}