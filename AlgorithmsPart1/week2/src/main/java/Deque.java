import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Dequeue. A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports
 * adding and removing items from either the front or the back of the data structure.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> firstItem = null;
    private Node<Item> lastItem = null;
    private int numberOfItems = 0;

    private class Node<T> {
        private T item;
        private Node next;
        private Node previous;
    }

    /**
     * construct an empty deque
     */
    public Deque() {
    }

    /**
     * @return is the deque empty?
     */
    public boolean isEmpty() {
        return firstItem == null || lastItem == null;
    }

    /**
     * @return the number of items on the deque
     */
    public int size() {
        return numberOfItems;
    }

    /**
     * insert the item at the front
     */
    public void addFirst(Item item) {
        validate(item);
        if (isEmpty())
            addItemToEmptyDeque(item);
        else
            addItemToTheBeggingOfTheDeque(item);
        numberOfItems++;
    }

    /**
     * insert the item at the end
     */
    public void addLast(Item item) {
        validate(item);
        if (isEmpty())
            addItemToEmptyDeque(item);
        else
            addItemToTheEndOfTheDeque(item);
        numberOfItems++;
    }

    /**
     * delete and return the item at the front
     */
    public Item removeFirst() {
        checkEmptyDeque();
        Item item = firstItem.item;
        firstItem = firstItem.next;
        if (firstItem == null)
            lastItem = null;
        else
            firstItem.previous = null;
        numberOfItems--;
        return item;
    }

    /**
     * delete and return the item at the end
     */
    public Item removeLast() {
        checkEmptyDeque();
        Item item = lastItem.item;
        lastItem = lastItem.previous;
        if (lastItem == null)
            firstItem = null;
        else
            lastItem.next = null;
        numberOfItems--;
        return item;
    }

    /**
     * @return an iterator over items in order from front to end
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator(firstItem);
    }

    private void addItemToEmptyDeque(Item item) {
        firstItem = createItem(item);
        lastItem = firstItem;
    }

    private void addItemToTheBeggingOfTheDeque(Item item) {
        Node<Item> oldFirstItem = firstItem;
        firstItem = createItem(item);
        firstItem.next = oldFirstItem;
        oldFirstItem.previous = firstItem;
    }

    private void addItemToTheEndOfTheDeque(Item item) {
        Node<Item> node = createItem(item);
        lastItem.next = node;
        node.previous = lastItem;
        lastItem = node;
    }

    private void validate(Item item) {
        if (item == null)
            throw new NullPointerException();
    }

    private Node<Item> createItem(Item item) {
        Node<Item> node = new Node<Item>();
        node.item = item;
        node.next = null;
        node.previous = null;
        return node;
    }

    private void checkEmptyDeque() {
        if (firstItem == null)
            throw new NoSuchElementException();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> currentNode;

        public DequeIterator(Node<Item> firstItem) {
            this.currentNode = firstItem;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item next() {
            checkForElements();
            Item item = currentNode.item;
            currentNode = currentNode.next;
            return item;
        }

        private void checkForElements() {
            if (currentNode == null)
                throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args)   // unit testing
    {
    }
}