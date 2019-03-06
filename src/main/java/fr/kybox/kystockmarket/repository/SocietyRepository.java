package fr.kybox.kystockmarket.repository;

import fr.kybox.kystockmarket.domain.entity.Society;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SocietyRepository extends ReactiveMongoRepository<Society, String> {
}
