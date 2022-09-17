package com.dokobit.fileupload.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration
{
//    The container will have a default thread pool to handle tasks. Define a separate thread pool that will be used by
//    Async calls and any task submitted/tracked by CompletableFuture
//    as below. In this example, we are defining a pool with 1000 threads.
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1000);
        executor.setMaxPoolSize(1000);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }
}
