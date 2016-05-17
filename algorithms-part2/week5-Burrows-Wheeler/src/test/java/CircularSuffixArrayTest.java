import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CircularSuffixArrayTest {
    @Test(expected = NullPointerException.class)
    public void throwsExceptionIfArgumentToConstructorIsNull() throws Exception {
        new CircularSuffixArray(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwExceptionIfIndexOutOfBounds() throws Exception {
        CircularSuffixArray sut = new CircularSuffixArray("alabala");

        sut.index(-1);
    }

    @Test
    public void returnCorrectResult() throws Exception {
        int[] correctIndexes = {11, 10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2};

        CircularSuffixArray sut = new CircularSuffixArray("ABRACADABRA!");

        for (int i = 0; i < correctIndexes.length; i++) {
            assertThat(sut.index(i), is(correctIndexes[i]));
        }
    }
}