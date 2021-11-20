package me.lucabartmann.crown.punish.listener;

import lombok.RequiredArgsConstructor;
import me.lucabartmann.crown.punish.PunishDataRepository;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuildLeaveListener extends ListenerAdapter {

    private final PunishDataRepository punishDataRepository;

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        // TODO: 20.11.2021 Implement punish data deletion of guild
    }
}
