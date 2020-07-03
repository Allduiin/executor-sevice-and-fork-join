import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.SumExecutorService;

public class ExecutorServiceTest {
    private static final long SUM_OF_NUMBERS = 500_000_500_000L;
    private static final long AMOUNT_OF_NUMBERS = 1_000_000L;
    private static final int AMOUNT_OF_THREADS = 10000;
    private List<Long> numbers;

    @Before
    public void setNumbers() {
        numbers = LongStream.rangeClosed(1, AMOUNT_OF_NUMBERS)
                .boxed()
                .collect(Collectors.toList());
    }

    @Test
    public void SumExecutorServiceTest() {
        SumExecutorService executorServiceCounter = new SumExecutorService(AMOUNT_OF_THREADS, numbers);
        long actuallySumOfNumbers = executorServiceCounter.execute();
        Assert.assertEquals(SUM_OF_NUMBERS, actuallySumOfNumbers);
    }
}
