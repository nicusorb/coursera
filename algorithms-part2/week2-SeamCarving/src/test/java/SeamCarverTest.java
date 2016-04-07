import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by nicubucalaete on 06/04/16.
 */
public class SeamCarverTest {

    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionWhenPassingNull() throws Exception {
        new SeamCarver(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void energyThrowsExceptionIfColumnIsLessThan0() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(10, 5));
        sut.energy(-1, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void energyThrowsExceptionIfColumnIsGreaterThanMaxWidth() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(10, 5));
        sut.energy(10, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void energyThrowsExceptionIfRowIsLessThan0() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(10, 5));
        sut.energy(1, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void energyThrowsExceptionIfRowIsGreaterThanMaxWidth() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(10, 5));
        sut.energy(1, 5);
    }

    @Test
    public void energyOfAPixelInFirstColumnIs_1000() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(10, 5));
        assertThat(sut.energy(0, 2), is(1000.0));
    }

    @Test
    public void energyOfAPixelInLastColumnIs_1000() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(10, 5));
        assertThat(sut.energy(9, 2), is(1000.0));
    }

    @Test
    public void energyOfAPixelInFirstRowIs_1000() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(10, 5));
        assertThat(sut.energy(1, 0), is(1000.0));
    }

    @Test
    public void energyOfAPixelInLastRowIs_1000() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(10, 5));
        assertThat(sut.energy(2, 4), is(1000.0));
    }

    @Test
    public void energyOfAMiddlePixelIsComputed() throws Exception {
        SeamCarver sut = new SeamCarver(new Picture(new File(getClass().getClassLoader().getResource("3x4.png").toURI())));

        assertThat(sut.energy(1, 2), is(Math.sqrt(52024)));
        assertThat(sut.energy(1, 1), is(Math.sqrt(52225)));
    }
}