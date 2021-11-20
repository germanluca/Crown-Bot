package me.lucabartmann.crown.guild.listener;

import lombok.RequiredArgsConstructor;
import me.lucabartmann.crown.guild.GuildData;
import me.lucabartmann.crown.guild.GuildDataRepository;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class GuildDataListener extends ListenerAdapter {

    private final GuildDataRepository guildDataRepository;

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        this.guildDataRepository.insert(GuildData.create(event.getGuild().getId())).subscribe();
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        this.guildDataRepository.deleteById(event.getGuild().getId()).subscribe();
    }

}
