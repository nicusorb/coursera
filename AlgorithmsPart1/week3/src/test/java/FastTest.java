import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FastTest extends AbstractTest {
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"input6.txt", "FastOutput6.txt"},
                {"input8.txt", "FastOutput8.txt"}});
    }

    public FastTest(String inputFileToBeChecked, String correctOutput) {
        super(inputFileToBeChecked, correctOutput);
    }

    @Override
    public void generateOutput() {
        Fast fast = new Fast();
        fast.main(new String[]{inputFileToBeChecked});
    }
}
