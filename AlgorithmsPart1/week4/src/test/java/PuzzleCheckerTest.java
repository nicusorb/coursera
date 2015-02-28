import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;


public class PuzzleCheckerTest {
    @Test
    public void checkAll() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File[] testFiles = new File(classLoader.getResource("8puzzle").getFile()).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });

        for (File file : testFiles) {
            // read in the board specified in the file
            In in = new In(file);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            System.out.println(file.getName() + ": " + solver.moves());
        }
    }

    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            System.out.println(filename + ": " + solver.moves());
        }
    }
}
