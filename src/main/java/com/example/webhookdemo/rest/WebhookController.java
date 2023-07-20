package com.example.webhookdemo.rest;

import com.example.webhookdemo.feign.TelegramFeign;
import com.example.webhookdemo.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/api/bot")
@RequiredArgsConstructor
public class WebhookController {
    private final TelegramService telegramService;

    @PostMapping
    public void getUpdates(@RequestBody Update update){
//        System.out.println(update);
        telegramService.analyzerMessage(update);
    }



}
