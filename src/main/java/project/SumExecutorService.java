package project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SumExecutorService {
    private final int numberOfThreads;
    private final List<Long> numbers;

    public SumExecutorService(int numberOfThreads, List<Long> numbers) {
        this.numberOfThreads = numberOfThreads;
        this.numbers = numbers;
    }

    public Long execute() {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Long>> results = new ArrayList<>(numberOfThreads);
        int potionOfNumbers = numbers.size() / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            Callable<Long> task = new ListSumThread(potionOfNumbers * i,
                    potionOfNumbers * i + potionOfNumbers, numbers);
            results.add(executorService.submit(task));
        }
        return sum(results);
    }

    private long sum(List<Future<Long>> results) {
        long sum = 0;
        for (Future<Long> result : results) {
            try {
                sum += result.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Can not count sum of the list of Future ", e);
            }
        }
        return sum;
    }
}
