package me.lucabartmann.crown;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Objects;

@Component
@PropertySource("classpath:application.properties")
public class CrownServerBeans {

    private final ShardManager shardManager;

    public CrownServerBeans(Environment environment) throws LoginException {
        this.shardManager = DefaultShardManagerBuilder.create(
                        environment.getProperty("bot.token"),
                        Arrays.asList(
                                GatewayIntent.DIRECT_MESSAGES,
                                GatewayIntent.GUILD_MESSAGES,
                                GatewayIntent.GUILD_MEMBERS,
                                GatewayIntent.GUILD_BANS,
                                GatewayIntent.GUILD_PRESENCES
                        )
                ).setShardsTotal(-1)
                .build();
        this.shardManager.setActivityProvider(value -> Activity.of(
                Objects.requireNonNull(environment.getProperty("bot.activityType", Activity.ActivityType.class)),
                Objects.requireNonNull(environment.getProperty("bot.activity")).replaceAll("%shard%", Integer.toString(value))
        ));
    }

    @Bean
    public ShardManager shardManager() {
        return this.shardManager;
    }

}
