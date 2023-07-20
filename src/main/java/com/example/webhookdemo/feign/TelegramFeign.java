package com.example.webhookdemo.feign;

import com.example.webhookdemo.message.RestConstanta;
import com.example.webhookdemo.model.ResultModel;
import com.example.webhookdemo.model.TelegramPhoto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FeignClient(url = RestConstanta.TELEGRAM_BASE_URL_WITHOUT_BOT, name = "FeignClient")
public interface TelegramFeign {

    @PostMapping("{path}/sendMessage")
    ResultModel sendMessageToUser(@PathVariable String path,
                                  @RequestBody SendMessage sendMessage);

    @PostMapping("{path}/sendPhoto")
    ResultModel sendPhotoToUser(@PathVariable String path,
                                @RequestBody TelegramPhoto telegramPhoto);
}
