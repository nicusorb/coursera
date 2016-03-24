import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WordNet {
    private Map<String, List<Integer>> nounToSynsetsIdsMap = new TreeMap<>();
    private Map<Integer, String> synsetsIdsToNounsMap = new TreeMap<>();
    private Digraph digraph;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        int nbOfSynsets = readSynsets(synsets);
        readHypernyms(hypernyms, nbOfSynsets);
        checkIfDigraphIsARootedDAG(digraph);
        sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToSynsetsIdsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounToSynsetsIdsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        checkForValidNouns(nounA, nounB);

        List<Integer> synsetsNounA = nounToSynsetsIdsMap.get(nounA);
        List<Integer> synsetsNounB = nounToSynsetsIdsMap.get(nounB);

        return sap.length(synsetsNounA, synsetsNounB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        checkForValidNouns(nounA, nounB);

        List<Integer> synsetsNounA = nounToSynsetsIdsMap.get(nounA);
        List<Integer> synsetsNounB = nounToSynsetsIdsMap.get(nounB);

        int ancestor = sap.ancestor(synsetsNounA, synsetsNounB);

        return synsetsIdsToNounsMap.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        new WordNet(args[0], args[1]);
    }

    private int readSynsets(String synsets) {
        int nbOfSynsets = 0;
        In synsetsIn = new In(synsets);

        while (synsetsIn.hasNextLine()) {
            String line = synsetsIn.readLine();
            String[] words = line.split(",");
            int synsetId = Integer.parseInt(words[0]);
            String[] nouns = words[1].split(" ");

            for (String noun : nouns) {
                List<Integer> synsetsIds = nounToSynsetsIdsMap.get(noun);
                if (synsetsIds == null) {
                    synsetsIds = new LinkedList<>();
                }
                synsetsIds.add(synsetId);
                nounToSynsetsIdsMap.put(noun, synsetsIds);
            }

            synsetsIdsToNounsMap.put(synsetId, words[1]);

            nbOfSynsets++;
        }

//        System.out.println(nbOfSynsets);
        return nbOfSynsets;
    }

    private void readHypernyms(String hypernyms, int nbOfSynsets) {
        In hypernymsIn = new In(hypernyms);
        digraph = new Digraph(nbOfSynsets);
        while (hypernymsIn.hasNextLine()) {
            String line = hypernymsIn.readLine();
            String[] fields = line.split(",");
            int synsetId = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int hypernym = Integer.parseInt(fields[i]);
                digraph.addEdge(synsetId, hypernym);
            }
        }
//        System.out.println(digraph.E());
    }

    private void checkIfDigraphIsARootedDAG(Digraph g) {
        Topological topological = new Topological(g);
        if (!topological.hasOrder())
            throw new IllegalArgumentException("This is not a DAG");
        int nbOfOutdegreesVertex = 0;
        for (int vertex = 0; vertex < g.V(); vertex++) {
            if (g.outdegree(vertex) == 0)
                nbOfOutdegreesVertex++;
        }
        if (nbOfOutdegreesVertex > 1)
            throw new IllegalArgumentException("This is not a rooted DAG");
    }

    private void checkForValidNouns(String nounA, String nounB) {
        if (!nounToSynsetsIdsMap.containsKey(nounA) || !nounToSynsetsIdsMap.containsKey(nounB))
            throw new IllegalArgumentException();
    }
}