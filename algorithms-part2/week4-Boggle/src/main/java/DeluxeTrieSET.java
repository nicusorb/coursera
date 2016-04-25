public class DeluxeTrieSET {
    private static final int R = 26;        // extended ASCII

    private Node root;      // root of trie
    private int N;          // number of keys in trie

    // R-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    /**
     * Initializes an empty set of strings.
     */
    public DeluxeTrieSET() {
    }

    /**
     * Does the set contain the given key?
     *
     * @param key the key
     * @return <tt>true</tt> if the set contains <tt>key</tt> and
     * <tt>false</tt> otherwise
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public boolean contains(String key) {
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = getCharAtPosition(key, d);
        return get(x.next[c], key, d + 1);
    }

    /**
     * Adds the key to the set if it is not already present.
     *
     * @param key the key to add
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString) N++;
            x.isString = true;
        } else {
            char c = getCharAtPosition(key, d);
            x.next[c] = add(x.next[c], key, d + 1);
        }
        return x;
    }

    public boolean hasKeyWithPrefix(String key) {
        Node x = get(root, key, 0);
        if (x == null)
            return false;
        return true;
    }

    private char getCharAtPosition(String key, int d) {
        return (char) (key.charAt(d) - 65);
    }
}
