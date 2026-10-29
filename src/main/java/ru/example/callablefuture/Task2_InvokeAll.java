package ru.example.callablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Task2_InvokeAll {
    public static void main(String[] args) throws Exception {
        System.out.println("\nЗадание 2: Запуск списка задач через invokeAll()");
        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Callable<Integer>> tasks = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            tasks.add(() -> {
                int sleepTime = 1000 + random.nextInt(2000);
                System.out.println("Задача " + taskId + " начала работу (спит " + sleepTime + " мс)");
                Thread.sleep(sleepTime);
                return taskId * 100;
            });
        }

        List<Future<Integer>> futures = executor.invokeAll(tasks);

        System.out.println("\nРезультаты выполнения:");
        for (int i = 0; i < futures.size(); i++) {
            Future<Integer> future = futures.get(i);
            if (future.isCancelled()) {
                System.out.println("Задача " + (i + 1) + " была отменена.");
            } else {
                System.out.println("Результат задачи " + (i + 1) + ": " + future.get());
            }
        }

        executor.shutdown();
    }
}