import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public abstract class AbstractTest {
    private static final String TEST_OUTPUT = "testOutput.txt";
    protected final String inputFileToBeChecked;
    private final String correctOutput;


    public AbstractTest(String inputFileToBeChecked, String correctOutput) {
        this.inputFileToBeChecked = inputFileToBeChecked;
        this.correctOutput = correctOutput;
    }

    @Test
    public void testOutput() throws Exception {
        redirectStdOutTo(TEST_OUTPUT);

        generateOutput();

        flushAndCloseStdOut();
        checkFileHaveIdenticalContent(correctOutput);
    }

    public abstract void generateOutput();

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
        In correctOutputs = new In(correctOutput);
        while (!testOutputs.isEmpty()) {
            String expected = testOutputs.readLine();
            String actual = correctOutputs.readLine();
            assertEquals(expected, actual);
        }

        testOutputs = new In(TEST_OUTPUT);
        correctOutputs = new In(correctOutput);

        while (!correctOutputs.isEmpty()) {
            assertEquals(testOutputs.readLine(), correctOutputs.readLine());
        }
    }
}
