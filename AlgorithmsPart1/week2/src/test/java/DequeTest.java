import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DequeTest {
    private Deque<String> deque = new Deque<String>();

    @Test
    public void testAddFirst() throws Exception {
        deque.addFirst("first1");
        deque.addFirst("first2");
        deque.addFirst("first3");

        assertFalse(deque.isEmpty());
        assertEquals(deque.size(), 3);
    }

    @Test(expected = NullPointerException.class)
    public void addFirst_throwsException() throws Exception {
        deque.addFirst(null);
    }

    @Test
    public void testAddLast() throws Exception {
        deque.addLast("last1");
        deque.addLast("last2");
        deque.addLast("last3");

        assertFalse(deque.isEmpty());
        assertEquals(deque.size(), 3);
    }

    @Test(expected = NullPointerException.class)
    public void addLast_throwsException() throws Exception {
        deque.addLast(null);
    }

    @Test
    public void testRemoveFirst() throws Exception {
        deque.addFirst("item1");
        deque.addFirst("item2");

        String item2 = deque.removeFirst();
        String item1 = deque.removeFirst();

        assertEquals(item2, "item2");
        assertEquals(item1, "item1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeFirst_throwsException() throws Exception {
        deque.removeFirst();
    }

    @Test
    public void testRemoveLast() throws Exception {
        deque.addLast("item1");
        deque.addLast("item2");

        String item2 = deque.removeLast();
        String item1 = deque.removeLast();

        assertEquals(item2, "item2");
        assertEquals(item1, "item1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeLast_throwsException() throws Exception {
        deque.removeLast();
    }

    @Test
    public void testAddFirstRemoveFirst_ConsecutiveOperations() throws Exception {
        deque.addFirst("item1");
        String item1 = deque.removeFirst();
        deque.addFirst("item2");
        String item2 = deque.removeFirst();

        assertEquals(item1, "item1");
        assertEquals(item2, "item2");
    }

    @Test
    public void testAddLastRemoveLast_ConsecutiveOperations() throws Exception {
        deque.addLast("item1");
        String item1 = deque.removeLast();
        deque.addLast("item2");
        String item2 = deque.removeLast();

        assertEquals(item1, "item1");
        assertEquals(item2, "item2");
    }
}
