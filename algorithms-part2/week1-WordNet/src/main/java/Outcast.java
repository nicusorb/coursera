import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String maxDistanceNoun = nouns[0];
        int maxDistance = -1;

        for (int i = 0; i < nouns.length; i++) {
            int sumDistances = 0;
            for (int j = 0; j < nouns.length; j++) {
                sumDistances += wordnet.distance(nouns[i], nouns[j]);
            }
            if (sumDistances > maxDistance) {
                maxDistanceNoun = nouns[i];
                maxDistance = sumDistances;
            }
        }

        return maxDistanceNoun;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}