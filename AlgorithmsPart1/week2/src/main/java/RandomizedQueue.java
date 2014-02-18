import java.util.Iterator;
import java.util.Random;

/**
 * A randomized queue is similar to a stack or queue, except that the item removed
 * is chosen uniformly at random from items in the data structure.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int maxSize = 10;
    private Item[] items;
    private int pos = 0;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = createNewArray(maxSize);
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
        validateItem(item);
        if (queueIsFull()) {
            resizeQueue();
        }
        items[pos++] = item;
    }

    private void validateItem(Item item) {
        if (item == null)
            throw new NullPointerException();
    }

    private boolean queueIsFull() {
        return pos == maxSize;
    }

    private void resizeQueue() {
        maxSize *= 2;
        Item[] newItems = createNewArray(maxSize);
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
    }

    private Item[] createNewArray(int size) {
        return (Item[]) new Object[size];
    }

    /**
     * delete and return a random item
     */
    public Item dequeue() {
        int randomPos = generateARandomPositionInArray();
        Item item = items[randomPos];
        shiftAllElementsOnePositionRightStartingFrom(randomPos);
        clearLastItemFromArray();
        pos--;
        return item;
    }

    /**
     * @return (but do not delete) a random item
     */
    public Item sample() {
        return items[generateARandomPositionInArray()];
    }

    private int generateARandomPositionInArray() {
        Random r = new Random();
        return r.nextInt(pos);
    }

    private void shiftAllElementsOnePositionRightStartingFrom(int randomPos) {
        for (int i = randomPos; i < pos - 1; i++)
            items[i] = items[i + 1];
    }

    private void clearLastItemFromArray() {
        items[pos] = null;
    }
//    public Iterator<Item> iterator()         // return an independent iterator over items in random order

    @Override
    public Iterator<Item> iterator() {
        return null;
    }
//    public static void main(String[] args)   // unit testing
}
