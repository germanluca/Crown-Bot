package me.lucabartmann.crown.guild;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "guild_data")
public final class GuildData {

    @Id
    private final String id;
    private final String prefix;
    private final Instant creationTime;

    private UUID apiKey = UUID.randomUUID();

    private GuildData(String id, String prefix, Instant creationTime) {
        this.id = id;
        this.prefix = prefix;
        this.creationTime = creationTime;
    }

    public static GuildData create(String guildId) {
        return new GuildData(guildId, "!", Instant.now());
    }

}
