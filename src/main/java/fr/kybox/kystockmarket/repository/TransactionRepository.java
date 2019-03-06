package fr.kybox.kystockmarket.repository;

import fr.kybox.kystockmarket.domain.entity.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
}
