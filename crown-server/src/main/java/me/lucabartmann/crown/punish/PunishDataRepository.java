package me.lucabartmann.crown.punish;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PunishDataRepository extends ReactiveMongoRepository<PunishData, UUID> {

    Mono<PunishData> findByTargetAndGuild(String target, String guild);

    Mono<PunishData> findByIdAndGuild(UUID id, String guild);

    Flux<PunishData> findAllByActive(boolean active);

    Flux<PunishData> findAllByGuild(String guild);

    void deleteAllByGuild(String guild);

}
