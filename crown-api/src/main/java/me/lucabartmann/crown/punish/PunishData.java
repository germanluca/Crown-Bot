package me.lucabartmann.crown.punish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "punish_data")
public final class PunishData {

    @Id
    private final UUID id;
    private final String guild;
    private final String target;
    private final String executor;
    private final PunishType type;
    private final String reason;
    private final Instant expirationTime;
    private final Instant creationTime;

    private boolean active = true;

    private PunishData(UUID id, String guild, String target, String executor, PunishType type, String reason, Instant expirationTime, Instant creationTime) {
        this.id = id;
        this.guild = guild;
        this.target = target;
        this.executor = executor;
        this.type = type;
        this.reason = reason;
        this.expirationTime = expirationTime;
        this.creationTime = creationTime;
    }

    public static PunishData create(String guild, String target, String executor, PunishType punishType, String reason, Instant expirationTime) {
        return new PunishData(UUID.randomUUID(), guild, target, executor, punishType, reason, expirationTime, Instant.now());
    }

    public boolean isActive() {
        return (this.expirationTime == Instant.EPOCH || Instant.now().isBefore(this.expirationTime)) && active;
    }

}
