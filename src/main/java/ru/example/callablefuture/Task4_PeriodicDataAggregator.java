package ru.example.callablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Task4_PeriodicDataAggregator {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\nЗадание 4: PeriodicDataAggregator");
        PeriodicDataAggregator aggregator = new PeriodicDataAggregator();
        aggregator.startAggregation();

        Thread.sleep(8000);
        aggregator.stopAggregation();
    }
}

class PeriodicDataAggregator {

    private final List<String> entityIds = Arrays.asList("USD", "EUR", "GBP", "JPY", "RUB");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ExecutorService workers = Executors.newFixedThreadPool(5);
    private final Random random = new Random();

    public void startAggregation() {
        scheduler.scheduleAtFixedRate(this::aggregateData, 0, 4, TimeUnit.SECONDS);
    }

    private void aggregateData() {
        System.out.println("\n[" + java.time.LocalTime.now() + "] Запуск нового цикла агрегации...");

        List<Future<String>> futures = new java.util.ArrayList<>();
        for (String id : entityIds) {
            futures.add(workers.submit(() -> fetchData(id)));
        }

        for (Future<String> future : futures) {
            try {
                String result = future.get(2, TimeUnit.SECONDS);
                System.out.println("Успешно получено: " + result);
            } catch (TimeoutException e) {
                System.out.println("Тайм-аут при получении данных!");
                future.cancel(true);
            } catch (Exception e) {
                System.out.println("Ошибка при получении данных: " + e.getMessage());
            }
        }
    }

    private String fetchData(String id) throws Exception {
        Thread.sleep(500 + random.nextInt(1000));


        if (random.nextInt(10) < 2) {
            throw new Exception("Сбой сети при запросе данных для " + id);
        }

        double price = 50 + random.nextDouble() * 50;
        return String.format("%s: %.2f", id, price);
    }

    public void stopAggregation() {
        System.out.println("\n>>> Остановка агрегатора...");
        scheduler.shutdown();
        workers.shutdown();
    }
}