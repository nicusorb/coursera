import java.util.Iterator;

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
}
