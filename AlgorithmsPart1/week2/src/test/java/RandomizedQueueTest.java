import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomizedQueueTest {
    private RandomizedQueue<Integer> sut = new RandomizedQueue<Integer>();

    @Test
    public void newlyCreatedQueue_shouldBeEmpty() throws Exception {
        assertTrue(sut.isEmpty());
    }

    @Test
    public void emptyQueue_hasSize_zero() throws Exception {
        assertEquals(0, sut.size());
    }

    @Test
    public void afterAddingAnItem_queue_isNotEmpty() throws Exception {
        sut.enqueue(1);

        assertEquals(1, sut.size());
        assertEquals(false, sut.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void whenAddingANullElement_throwException() throws Exception {
        sut.enqueue(null);
    }

    @Test
    public void canAddElementsMoreThanInitialSize() throws Exception {
        for (int i = 0; i < 20; i++) {
            sut.enqueue(i);
        }

        assertEquals(20, sut.size());
    }
}
