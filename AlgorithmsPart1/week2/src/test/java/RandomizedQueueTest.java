import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

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

    @Test
    public void dequeue_returnsARandomItem() throws Exception {
        sut.enqueue(1);

        assertEquals(new Integer(1), sut.dequeue());
        assertEquals(0, sut.size());
    }

    @Test
    public void test_multipleDequeue() throws Exception {
        int[] toAdd = {1, 2, 3, 4, 5, 6, 7};
        int[] dequeue = new int[toAdd.length];

        for (int i : toAdd) {
            sut.enqueue(i);
        }

        for (int i = 0; i < toAdd.length; i++) {
            dequeue[i] = sut.dequeue();
        }

        assertEquals(0, sut.size());
        for (int i : dequeue) {
            boolean found = false;
            for (int k : toAdd) {
                if (i == k) {
                    found = true;
                    break;
                }
            }

            if (!found)
                fail();
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void whenDequeueAnEmptyQueue_throwException() throws Exception {
        sut.dequeue();
    }

//    @Test
//    public void resizeArray_onMultipleDequeue() throws Exception {
//        for (int i = 0; i < 5; i++) {
//            sut.enqueue(i);
//        }
//
//    }

    @Test
    public void sample_returnsARandomItem() throws Exception {
        sut.enqueue(1);

        assertEquals(new Integer(1), sut.sample());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenSampleAnEmptyQueue_throwException() throws Exception {
        sut.sample();
    }
}