package me.lucabartmann.crown.guild;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "guild_data")
public final record GuildData(@Id String id,
                              String prefix,
                              Instant creationTime) {

    public static GuildData create(String guildId) {
        return new GuildData(guildId, "!", Instant.now());
    }

}
