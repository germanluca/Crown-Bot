package me.lucabartmann.crown.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ShardManager shardManager;

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        final ApplicationContext applicationContext = event.getApplicationContext();

        log.info("Registering discord ListenerAdapters...");
        applicationContext.getBeansOfType(ListenerAdapter.class).forEach((s, listenerAdapter) ->
                this.shardManager.addEventListener(listenerAdapter)
        );
    }
}
