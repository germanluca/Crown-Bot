package me.lucabartmann.crown.guild;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface GuildDataRepository extends ReactiveMongoRepository<GuildData, String> {

    Mono<GuildData> findByApiKey(UUID apiKey);

}
