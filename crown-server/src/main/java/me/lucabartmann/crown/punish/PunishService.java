package me.lucabartmann.crown.punish;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class PunishService {

    private final PunishDataRepository punishDataRepository;

    public CompletableFuture<PunishData> findPunish(@NotNull Member target) {
        final CompletableFuture<PunishData> completableFuture = new CompletableFuture<>();

        this.punishDataRepository.findByTargetAndGuild(target.getId(), target.getGuild().getId()).subscribe(punishData -> {
            if (punishData == null) return;

            completableFuture.completeAsync(() -> punishData);
        });

        return completableFuture;
    }

    @CanIgnoreReturnValue
    public CompletableFuture<PunishData> punish(@NotNull Member target,
                                                @NotNull Member executor,
                                                @NotNull PunishType punishType,
                                                @Nullable String reason,
                                                long time,
                                                @NotNull TimeUnit timeUnit) {
        final CompletableFuture<PunishData> completableFuture = new CompletableFuture<>();
        final Guild guild = target.getGuild();

        this.punishDataRepository.findByTargetAndGuild(target.getId(), guild.getId()).hasElement().subscribe(hasElement -> {
            if (hasElement) return;

            final PunishData punishData = PunishData.create(
                    guild.getId(),
                    target.getId(),
                    executor.getId(),
                    punishType,
                    reason,
                    time == -1 ? Instant.EPOCH : Instant.now().plus(time, timeUnit.toChronoUnit())
            );

            this.punishDataRepository.insert(punishData).subscribe();
            completableFuture.completeAsync(() -> punishData);
        });

        return completableFuture;
    }

    @CanIgnoreReturnValue
    public CompletableFuture<PunishData> punish(@NotNull Member target,
                                                @NotNull Member executor,
                                                @NotNull PunishType punishType,
                                                @Nullable String reason) {
        return this.punish(target, executor, punishType, reason, -1, TimeUnit.HOURS);
    }

    @CanIgnoreReturnValue
    public CompletableFuture<PunishData> pardon(@NotNull PunishData punishData) {
        punishData.setActive(false);

        return CompletableFuture.supplyAsync(() -> {
            this.punishDataRepository.save(punishData).subscribe();
            return punishData;
        });
    }

    @Async
    @Scheduled(fixedDelay = 1L, timeUnit = TimeUnit.MINUTES)
    public void checkPunishments() {
        this.punishDataRepository.findAllByActive(true)
                .collectList()
                .subscribe(punishDataList -> {
                    punishDataList.stream().filter(punishData -> !punishData.isActive())
                            .forEach(this::pardon);
                });
    }

}
