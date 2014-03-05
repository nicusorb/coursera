import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SolverTest extends AbstractTest {
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"puzzle04.txt", "puzzle04Output.txt"}});
    }

    public SolverTest(String inputFileToBeChecked, String correctOutput) {
        super(inputFileToBeChecked, correctOutput);
    }

    @Override
    public void generateOutput() {
        Solver.main(new String[]{inputFileToBeChecked});
    }
}
