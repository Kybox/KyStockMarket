package fr.kybox.kystockmarket.repository;

import fr.kybox.kystockmarket.domain.entity.Society;
import fr.kybox.kystockmarket.domain.entity.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    Flux<Transaction> findBySociety(Society society);
}
