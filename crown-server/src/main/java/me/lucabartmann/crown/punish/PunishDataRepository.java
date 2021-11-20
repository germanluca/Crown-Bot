package me.lucabartmann.crown.punish;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PunishDataRepository extends ReactiveMongoRepository<PunishData, UUID> {
}
