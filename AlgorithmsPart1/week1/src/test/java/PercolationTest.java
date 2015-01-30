import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PercolationTest {

    private Percolation sut;

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwExceptionIfNumberOfSitesIsNotPositive() throws Exception {
        Percolation sut = new Percolation(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void openShouldTrowExceptionIfRowOutOfBoundaries() throws Exception {
        sut = new Percolation(4);

        sut.open(5, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void openShouldTrowExceptionIfColumnOutOfBoundaries() throws Exception {
        sut = new Percolation(4);

        sut.open(2, 5);
    }

}
