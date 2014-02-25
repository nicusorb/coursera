import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BruteTest {
    private static final String TEST_OUTPUT = "testOutput.txt";
    private final String inputFileToBeChecked;
    private final String correctOutput;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"input6.txt", "BruteOutput6.txt"},
                {"input8.txt", "BruteOutput8.txt"}});
    }

    public BruteTest(String inputFileToBeChecked, String correctOutput) {
        this.inputFileToBeChecked = inputFileToBeChecked;
        this.correctOutput = correctOutput;
    }

    @Test
    public void testBruteOutput() throws Exception {
        redirectStdOutTo(TEST_OUTPUT);

        Brute brute = new Brute();
        brute.main(new String[]{inputFileToBeChecked});

        flushAndCloseStdOut();
        checkFileHaveIdenticalContent(correctOutput);
    }

    private void redirectStdOutTo(String fileName) throws FileNotFoundException {
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(fileName)));
        System.setOut(out);
        System.setErr(out);
    }

    private void flushAndCloseStdOut() {
        System.out.flush();
        System.out.close();
    }

    private void checkFileHaveIdenticalContent(String correctOutput) {
        In testOutputs = new In(TEST_OUTPUT);
        In bruteOutputs = new In(correctOutput);
        while (!testOutputs.isEmpty()) {
            assertEquals(testOutputs.readLine(), bruteOutputs.readLine());
        }

        testOutputs = new In(TEST_OUTPUT);
        bruteOutputs = new In(correctOutput);

        while (!bruteOutputs.isEmpty()) {
            assertEquals(testOutputs.readLine(), bruteOutputs.readLine());
        }
    }
}
