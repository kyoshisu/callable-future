package ru.example.callablefuture;

import java.util.concurrent.*;

public class Task1_CancelFuture {
    public static void main(String[] args) throws Exception {
        System.out.println("Задание 1: Отмена задачи через Future.cancel()");
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> longTask = () -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Долгая задача выполняется... Секунда " + i);
                Thread.sleep(1000);
            }
            return "Задача выполнена полностью!";
        };

        Future<String> future = executor.submit(longTask);

        Thread.sleep(3000);
        System.out.println(">>> Попытка отменить задачу...");
        boolean cancelled = future.cancel(true);
        System.out.println("Задача отменена: " + cancelled);

        try {
            String result = future.get();
            System.out.println("Результат: " + result);
        } catch (CancellationException e) {
            System.out.println("Поймано исключение: задача была отменена!");
        }

        executor.shutdown();
    }
}