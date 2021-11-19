package me.lucabartmann.crown.guild;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "guild_data")
public record GuildData(@Id String id,
                        String prefix,
                        Instant creationTime) {
}
