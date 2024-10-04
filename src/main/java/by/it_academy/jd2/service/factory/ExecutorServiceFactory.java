package by.it_academy.jd2.service.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceFactory {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private ExecutorServiceFactory() {}

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static void closeExecutorService() {
        executorService.shutdown();
        try {
           if(!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
               executorService.shutdownNow();
           }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }


}
