import org.junit.Test;

public class PercolationStatsTest {
    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionIfNegativeParameters() throws Exception {
        new PercolationStats(0, 0);
    }
}