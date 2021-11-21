package me.lucabartmann.crown.punish.controller;

import me.lucabartmann.crown.punish.PunishData;
import me.lucabartmann.crown.punish.PunishType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public interface PunishDataController {

    @GetMapping("data/{id}")
    ResponseEntity<PunishData> findPunishData(@PathVariable UUID id, @RequestHeader("X-API-KEY") String apiKey);

    @GetMapping("data/{member}")
    ResponseEntity<PunishData> findPunishDataByMember(@PathVariable String member, @RequestHeader("X-API-KEY") String apiKey);

    @GetMapping("list/")
    ResponseEntity<List<PunishData>> findAllPunishData(@RequestHeader("X-API-KEY") String apiKey);

    @PostMapping("punish/")
    ResponseEntity<PunishData> punish(@RequestParam String target,
                                      @RequestParam String executor,
                                      @RequestParam PunishType punishType,
                                      @RequestParam(required = false) String reason,
                                      @RequestParam(required = false, defaultValue = "-1") long time,
                                      @RequestParam TimeUnit timeUnit,
                                      @RequestHeader("X-API-KEY") String apiKey) throws ExecutionException, InterruptedException;

    @PostMapping("pardon/")
    ResponseEntity<PunishData> pardon(@RequestBody PunishData punishData, @RequestHeader("X-API-KEY") String apiKey) throws ExecutionException, InterruptedException;

}
