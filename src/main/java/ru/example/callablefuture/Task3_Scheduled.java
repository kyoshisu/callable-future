package ru.example.callablefuture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task3_Scheduled {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\nЗадание 3: Периодическая задача (ScheduledExecutorService)");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable periodicTask = () -> {
            System.out.println("[Мониторинг] Проверка статуса системы... " + java.time.LocalTime.now());
        };

        scheduler.scheduleAtFixedRate(periodicTask, 0, 2, TimeUnit.SECONDS);

        Thread.sleep(7000);
        System.out.println(">>> Остановка планировщика...");
        scheduler.shutdown();
    }
}