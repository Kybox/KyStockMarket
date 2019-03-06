package fr.kybox.kystockmarket.web.controller;

import fr.kybox.kystockmarket.domain.entity.Society;
import fr.kybox.kystockmarket.domain.entity.Transaction;
import fr.kybox.kystockmarket.repository.SocietyRepository;
import fr.kybox.kystockmarket.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
}
