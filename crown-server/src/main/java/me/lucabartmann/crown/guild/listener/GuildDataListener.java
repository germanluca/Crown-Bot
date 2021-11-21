package me.lucabartmann.crown.guild.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lucabartmann.crown.guild.GuildData;
import me.lucabartmann.crown.guild.GuildDataRepository;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class GuildDataListener extends ListenerAdapter {

    private final GuildDataRepository guildDataRepository;

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        final GuildData guildData = GuildData.create(event.getGuild().getId());

        this.guildDataRepository.insert(guildData).subscribe();
        log.info("Created guild data for " + guildData.getId() + " with API-KEY: " + guildData.getApiKey());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        this.guildDataRepository.deleteById(event.getGuild().getId()).subscribe();
    }

}
