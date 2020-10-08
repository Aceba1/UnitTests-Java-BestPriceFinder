package com.aceba1.getQuote;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
	// write your code here
        var start = LocalTime.now();

        var service = new FlightService();
        try {
            CompletableFuture.allOf(
                service.GetQuotes()
                    .map(future -> future.thenAccept(System.out::println))
                    .collect(Collectors.toList())
                    .toArray(CompletableFuture[]::new))
                .thenRun(() -> {
                    var end = LocalTime.now();
                    System.out.println("All sites responded in " + Duration.between(start, end).toMillis() + "ms");
                }).get(30, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
