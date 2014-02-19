import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class Subset {
    public static void main(String[] args) {
        int nbOfElementsToPrint = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        readInputStrings(randomizedQueue);

        Iterator<String> it = randomizedQueue.iterator();
        while (nbOfElementsToPrint > 0) {
            System.out.println(it.next());
            nbOfElementsToPrint--;
        }
    }

    private static void readInputStrings(RandomizedQueue<String> randomizedQueue) {
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
    }

    private static class RandomizedQueue<Item> implements Iterable<Item> {
        private Item[] items;
        private int pos = 0;

        /**
         * construct an empty randomized queue
         *
         * @param size
         */
        public RandomizedQueue() {
            items = createNewArray(10);
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
            if (arrayIsFull()) {
                resize(items.length * 2);
            }
            items[pos++] = item;
        }

        private void validateItem(Item item) {
            if (item == null)
                throw new NullPointerException();
        }

        private boolean arrayIsFull() {
            return pos == items.length;
        }

        private void resize(int size) {
            Item[] newItems = createNewArray(size);
            System.arraycopy(items, 0, newItems, 0, pos);
            items = newItems;
        }

        private Item[] createNewArray(int size) {
            return (Item[]) new Object[size];
        }

        /**
         * @return an independent iterator over items in random order
         */
        @Override
        public Iterator<Item> iterator() {
            return new RandomizedQueueIterator();
        }

        private class RandomizedQueueIterator implements Iterator<Item> {
            private final Item[] shuffledArray;
            private int shuffledArrayPos = 0;

            RandomizedQueueIterator() {
                shuffledArray = createNewArray(size());
                System.arraycopy(items, 0, shuffledArray, 0, shuffledArray.length);

                for (int i = 0; i < shuffledArray.length; i++)
                    exchangeValues(i, generateRandomValue(i + 1));
            }

            @Override
            public boolean hasNext() {
                return shuffledArrayPos < shuffledArray.length;
            }

            @Override
            public Item next() {
                checkIfThereAreMoreElements();
                return shuffledArray[shuffledArrayPos++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private int generateRandomValue(int i) {
                Random r = new Random();
                return r.nextInt(i);
            }

            private void exchangeValues(int i, int j) {
                Item temp = shuffledArray[i];
                shuffledArray[i] = shuffledArray[j];
                shuffledArray[j] = temp;
            }

            private void checkIfThereAreMoreElements() {
                if (!areMoreElements())
                    throw new NoSuchElementException();
            }

            private boolean areMoreElements() {
                return shuffledArrayPos < shuffledArray.length;
            }
        }
    }
}
