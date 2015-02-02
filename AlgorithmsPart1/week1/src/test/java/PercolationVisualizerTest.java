import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

public class PercolationVisualizerTest {
    @Test
    public void openAll() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File[] testFiles = new File(classLoader.getResource(".").getFile()).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });

        for (File testFile : testFiles) {
            System.out.println("Running percolation file: " + testFile.getName());
            String[] fileName = {testFile.getAbsolutePath(), "0"};
            PercolationVisualizer.main(fileName);
            Thread.sleep(100);
        }
    }
}
