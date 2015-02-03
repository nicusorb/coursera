import org.junit.Test;

public class PercolationTest {

    private Percolation sut;

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionIfNumberOfSitesIsNotPositive() throws Exception {
        Percolation sut = new Percolation(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void openShouldTrowExceptionIfRowOutOfBoundaries() throws Exception {
        sut = new Percolation(4);

        sut.open(0, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void openShouldTrowExceptionIfColumnOutOfBoundaries() throws Exception {
        sut = new Percolation(4);

        sut.open(2, 5);
    }

}
