package me.lucabartmann.crown.guild;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildDataRepository extends ReactiveMongoRepository<GuildData, String> {
}
