package me.lucabartmann.crown.punish.controller;

import lombok.RequiredArgsConstructor;
import me.lucabartmann.crown.guild.GuildData;
import me.lucabartmann.crown.guild.GuildDataRepository;
import me.lucabartmann.crown.punish.PunishData;
import me.lucabartmann.crown.punish.PunishDataRepository;
import me.lucabartmann.crown.punish.PunishService;
import me.lucabartmann.crown.punish.PunishType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
public final class PunishDataControllerImplementation implements PunishDataController {

    private final ShardManager shardManager;
    private final PunishService punishService;
    private final GuildDataRepository guildDataRepository;
    private final PunishDataRepository punishDataRepository;

    @Override
    public ResponseEntity<PunishData> findPunishData(UUID id, String apiKey) {
        final GuildData guildData = this.guildDataRepository.findByApiKey(UUID.fromString(apiKey)).block();
        return ResponseEntity.ok(this.punishDataRepository.findByIdAndGuild(id, guildData.getId()).block());
    }

    @Override
    public ResponseEntity<PunishData> findPunishDataByMember(String member, String apiKey) {
        final GuildData guildData = this.guildDataRepository.findByApiKey(UUID.fromString(apiKey)).block();
        return ResponseEntity.ok(this.punishDataRepository.findByTargetAndGuild(member, guildData.getId()).block());
    }

    @Override
    public ResponseEntity<List<PunishData>> findAllPunishData(String apiKey) {
        final GuildData guildData = this.guildDataRepository.findByApiKey(UUID.fromString(apiKey)).block();
        return ResponseEntity.ok(this.punishDataRepository.findAllByGuild(guildData.getId()).collectList().block());
    }

    @Override
    public ResponseEntity<PunishData> punish(String target, String executor, PunishType punishType, String reason, long time, TimeUnit timeUnit, String apiKey) throws ExecutionException, InterruptedException {
        final GuildData guildData = this.guildDataRepository.findByApiKey(UUID.fromString(apiKey)).block();

        final Guild guild = this.shardManager.getGuildById(guildData.getId());
        if (guild == null) return ResponseEntity.internalServerError().build();

        final Member targetMember = guild.getMemberById(target);
        final Member executorMember = guild.getMemberById(executor);

        if (targetMember == null || executorMember == null) return ResponseEntity.internalServerError().build();

        return ResponseEntity.ok(this.punishService.punish(targetMember, executorMember, punishType, reason, time, timeUnit).get());
    }

    @Override
    public ResponseEntity<PunishData> pardon(PunishData punishData, String apiKey) throws ExecutionException, InterruptedException {
        final GuildData guildData = this.guildDataRepository.findByApiKey(UUID.fromString(apiKey)).block();
        if (!guildData.getId().equals(punishData.getGuild()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(this.punishService.pardon(punishData).get());
    }
}
