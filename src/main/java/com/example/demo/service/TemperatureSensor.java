package com.example.demo.service;

import com.example.demo.model.Temperature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class TemperatureSensor {
    private final ApplicationEventPublisher publisher;
    private final Random rnd = new Random();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();


    public TemperatureSensor(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void startProcessing(){
        this.executorService.schedule(this::probe,1,SECONDS);
    }

    private void probe(){
        double temperature = 16 + rnd.nextGaussian()*10;
        publisher.publishEvent(new Temperature(temperature));
        executorService.schedule(
                this::probe, rnd.nextInt(3), SECONDS
        );
    }

    @Async
    @EventListener
    public void printTemperature(Temperature temperature){
        System.out.println(temperature.toString());
    }
}
