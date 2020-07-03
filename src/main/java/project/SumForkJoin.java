package project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class SumForkJoin extends RecursiveTask<Long> {
    private static final int THRESHOLD = 100;
    private final List<Long> numbers;

    public SumForkJoin(List<Long> numbers) {
        super();
        this.numbers = numbers;
    }

    @Override
    public Long compute() {
        if (numbers.size() - 1 > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks())
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        }
        return numbers.stream().reduce(0L, Long::sum);
    }

    private Collection<RecursiveTask<Long>> createSubTasks() {
        final List<RecursiveTask<Long>> dividedTasks = new ArrayList<>();
        dividedTasks.add(new SumForkJoin(numbers.subList(0, numbers.size() / 2)));
        dividedTasks.add(new SumForkJoin(numbers.subList(numbers.size() / 2,
                numbers.size())));
        return dividedTasks;
    }
}
