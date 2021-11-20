package me.lucabartmann.crown.punish;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "punish_data")
public record PunishData(@Id UUID id,
                         String target,
                         String executor,
                         PunishType punishType,
                         Instant expirationTime,
                         Instant creationTime) {

    public boolean isActive() {
        return this.expirationTime == Instant.EPOCH || Instant.now().isBefore(this.expirationTime);
    }

}
