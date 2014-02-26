import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BruteTest extends AbstractTest {
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"input6.txt", "BruteOutput6.txt"},
                {"input8.txt", "BruteOutput8.txt"}});
    }

    public BruteTest(String inputFileToBeChecked, String correctOutput) {
        super(inputFileToBeChecked, correctOutput);
    }

    @Override
    public void generateOutput() {
        Brute brute = new Brute();
        brute.main(new String[]{inputFileToBeChecked});
    }
}
