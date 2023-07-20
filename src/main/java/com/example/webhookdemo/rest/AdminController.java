package com.example.webhookdemo.rest;

import com.example.webhookdemo.feign.TelegramFeign;
import com.example.webhookdemo.message.RestConstanta;
import com.example.webhookdemo.model.ResultModel;
import com.example.webhookdemo.model.TelegramPhoto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.webhookdemo.message.RestConstanta.BOT_TOKEN;
import static com.example.webhookdemo.message.RestConstanta.MY_URL;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final TelegramFeign telegramFeign;

    Set<String> usersSet = new HashSet<>(List.of("617049958", "1073305407"));

    @GetMapping("/send")
    public String sendMessageToAllUsers(@RequestParam String text) {
        for (String chatId : usersSet) {
            SendMessage sendMessage = new SendMessage(chatId, text);
            ResultModel resultModel = telegramFeign.sendMessageToUser("/bot" + BOT_TOKEN, sendMessage);

            System.out.println(resultModel);
        }
        return "OK";
    }


    @PostMapping("/sendPhoto")
    public String sendPhotoToAllUsers(@RequestParam String text,
                                      @RequestParam String fileName) {
        String fileId = null;
        try {
            for (String chatId : usersSet) {
                fileName = fileId == null ? MY_URL + "/api/attachment?fileName=" + fileName : fileId;
                TelegramPhoto telegramPhoto = new TelegramPhoto(chatId, fileName, text);
                ResultModel resultModel = telegramFeign.sendPhotoToUser("/bot" + BOT_TOKEN, telegramPhoto);

                if (fileId == null) {
                    List<PhotoSize> photos = resultModel.getResult().getPhoto();
                    fileId = photos.get(photos.size() - 1).getFileId();
//                    fileId = photoSize.getFileId();

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "Send";
    }

}
