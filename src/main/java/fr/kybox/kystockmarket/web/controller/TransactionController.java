package fr.kybox.kystockmarket.web.controller;

import fr.kybox.kystockmarket.domain.entity.Society;
import fr.kybox.kystockmarket.domain.entity.Transaction;
import fr.kybox.kystockmarket.repository.SocietyRepository;
import fr.kybox.kystockmarket.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final SocietyRepository societyRepository;

    public TransactionController(TransactionRepository transactionRepository,
                                 SocietyRepository societyRepository) {
        this.transactionRepository = transactionRepository;
        this.societyRepository = societyRepository;
    }

    @GetMapping(value = "/transaction/all")
    public Flux<Transaction> findAll() {

        return transactionRepository.findAll();
    }

    @GetMapping(value = "/transaction/{id}")
    public Mono<Transaction> findById(@PathVariable String id) {

        return transactionRepository.findById(id);
    }

    @PostMapping(value = "transaction/add")
    public Mono<Transaction> save(@RequestBody Transaction transaction) {

        return transactionRepository.save(transaction);
    }

    @DeleteMapping(value = "transaction/delete/{id}")
    public Mono<Void> delete(@PathVariable String id) {

        return transactionRepository.deleteById(id);
    }

    @PutMapping(value = "transaction/update/{id}")
    public Mono<Transaction> update(@RequestBody Transaction transaction, @PathVariable String id) {

        transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    @GetMapping(value = "transaction/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> streamTransaction() {

        return transactionRepository.findAll();
    }

    @GetMapping(value = "transaction/stream/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Transaction> streamTransactionById(@PathVariable String id) {

        return societyRepository
                .findById(id)
                .flatMapMany(s -> {

                    Flux<Long> delay = Flux.interval(Duration.ofMillis(1000));
                    Flux<Transaction> transactionFlux = Flux.fromStream(Stream.generate(() -> {

                        Transaction transaction = new Transaction();
                        transaction.setInstant(Instant.now());
                        transaction.setSociety(s);
                        transaction.setPrice(s.getPrice() * (1 + (Math.random() * 10 - 6) / 100));
                        return transaction;
                    }));

            return Flux.zip(delay, transactionFlux).map(Tuple2::getT2);
        });
    }
}
