package fr.kybox.kystockmarket;

import fr.kybox.kystockmarket.domain.entity.Society;
import fr.kybox.kystockmarket.domain.entity.Transaction;
import fr.kybox.kystockmarket.repository.SocietyRepository;
import fr.kybox.kystockmarket.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class KyStockMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(KyStockMarketApplication.class, args);
    }

    @Bean
    CommandLineRunner start(SocietyRepository societyRepository, TransactionRepository transactionRepository) {

        return args -> {

            societyRepository
                    .deleteAll()
                    .subscribe(null, null, ()-> {
                        Stream.of("BP", "SG", "BNP", "AXA")
                            .forEach(s -> {
                                societyRepository
                                        .save(new Society(s, s, Math.random() * 1000))
                                        .subscribe(System.out::println);
                            });

                        transactionRepository
                                .deleteAll()
                                .subscribe(null, null, ()-> {
                                    Stream.of("BP", "SG", "BNP", "AXA")
                                            .forEach(s -> societyRepository
                                                    .findById(s)
                                                    .subscribe(society -> {
                                                        for (int i = 0; i < 10; i++){
                                                            Transaction t = new Transaction();
                                                            t.setInstant(Instant.now());
                                                            t.setSociety(society);
                                                            t.setPrice(society.getPrice() * (1 + (Math.random() * 12 - 6) / 100));
                                                            transactionRepository
                                                                    .save(t)
                                                                    .subscribe(System.out::println);
                                                        }
                                                    })
                                            );
                                });
                    });

            System.out.println("This line is displayed before all operations");
        };
    }
}
