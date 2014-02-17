import java.util.Iterator;

/**
 * A randomized queue is similar to a stack or queue, except that the item removed
 * is chosen uniformly at random from items in the data structure.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int MAX_SIZE = 10;
    private Item[] items;
    private int pos = 0;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[MAX_SIZE];
    }

    /**
     * is the queue empty?
     */
    public boolean isEmpty() {
        return pos == 0;
    }

    /**
     * @return the number of items on the queue
     */
    public int size() {
        return pos;
    }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        items[pos++] = item;
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }
//    public Item dequeue()                    // delete and return a random item
//    public Item sample()                     // return (but do not delete) a random item
//    public Iterator<Item> iterator()         // return an independent iterator over items in random order
//    public static void main(String[] args)   // unit testing
}
