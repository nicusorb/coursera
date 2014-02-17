import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Dequeue. A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports
 * inserting and removing items from either the front or the back of the data structure. Create a generic data type Deque that implements the following API:
 * <p/>
 * public class Deque<Item> implements Iterable<Item> {
 * public Deque()                           // construct an empty deque
 * public boolean isEmpty()                 // is the deque empty?
 * public int size()                        // return the number of items on the deque
 * public void addFirst(Item item)          // insert the item at the front
 * public void addLast(Item item)           // insert the item at the end
 * public Item removeFirst()                // delete and return the item at the front
 * public Item removeLast()                 // delete and return the item at the end
 * public Iterator<Item> iterator()         // return an iterator over items in order from front to end
 * public static void main(String[] args)   // unit testing
 * }
 * Throw a NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException
 * if the client attempts to remove an item from an empty deque; throw an UnsupportedOperationException if the client
 * calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls the next() method
 * in the iterator and there are no more items to return.
 * <p/>
 * Your deque implementation must support each deque operation in constant worst-case time and use space proportional
 * to the number of items currently in the deque. Additionally, your iterator implementation must support the operations
 * next() and hasNext() (plus construction) in constant worst-case time and use a constant amount of extra space per iterator.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> firstItem = null;
    private Node<Item> lastItem = null;
    private int numberOfItems = 0;

    private class Node<T> {
        T item;
        Node next;
        Node previous;
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
        checkItemForNull(item);
        Node<Item> oldFirstItem = firstItem;
        firstItem = createItem(item);
        firstItem.next = oldFirstItem;
        numberOfItems++;
        if (isEmpty()) {
            lastItem = firstItem;
        } else if (oldFirstItem != null) {
            oldFirstItem.previous = firstItem;
        }
    }

    /**
     * insert the item at the end
     */
    public void addLast(Item item) {
        checkItemForNull(item);
        Node<Item> node = createItem(item);
        if (firstItem == null) {
            firstItem = node;
            lastItem = node;
        } else {
            lastItem.next = node;
            node.previous = lastItem;
            lastItem = node;
        }
        numberOfItems++;
    }

    private void checkItemForNull(Item item) {
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

    /**
     * delete and return the item at the front
     */
    public Item removeFirst() {
        checkEmptyDeque();
        Item item = firstItem.item;
        firstItem = firstItem.next;
        if (!isEmpty())
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
        if (lastItem != null)
            lastItem.next = null;
        checkDequeForNull();
        numberOfItems--;
        return item;
    }

    private void checkEmptyDeque() {
        if (firstItem == null)
            throw new UnsupportedOperationException();
    }

    private void checkDequeForNull() {
        if (lastItem == null) {
            firstItem = null;
        }
    }

    /**
     * @return an iterator over items in order from front to end
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator(firstItem);
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