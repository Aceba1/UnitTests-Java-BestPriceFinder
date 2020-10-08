package com.aceba1.getQuote;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class FlightService {

    public Stream<CompletableFuture<Quote>> GetQuotes() {
        return GetQuotes("site1", "site2", "site3", "site4");
    }

    public Stream<CompletableFuture<Quote>> GetQuotes(String... sites) {
        return Arrays.stream(sites)
            .map(this::GetQuote);
    }

    public CompletableFuture<Quote> GetQuote(String site) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Getting a quote from " + site);

            var random = new Random();

            try {
                Thread.sleep(1000 + random.nextInt(4000));
            } catch (InterruptedException e) {
                System.out.println("Cancelled " + site);
            }

            int price = 100 + random.nextInt(100);
            return new Quote(site, price);
        });
    }
}
