package com.example.webhookdemo.service;

import com.example.webhookdemo.feign.TelegramFeign;
import com.example.webhookdemo.message.RestConstanta;
import com.example.webhookdemo.model.ResultModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.webhookdemo.message.RestConstanta.*;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final RestTemplate restTemplate;
    private final String code = "KOD=admin";
    private final TelegramFeign telegramFeign;

    public void whenStart(Update update) {
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Xush kelibsiz!");
        ResultModel object = restTemplate.postForObject(TELEGRAM_BASE_URL + BOT_TOKEN + "/sendMessage", sendMessage, ResultModel.class);
        System.out.println(object);

    }

    public void whenService(Update update) {
        String service = "Bizda mavjud bo'lgan servicelar\n /start -> boshlash uchun \n " +
                "/services -> hizmatlardan foydalanish uchun\n" +
                "/admin -> admin uchun \n" +
                "/poll -> polling uchun";
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), service);
        Object object = restTemplate.postForObject(TELEGRAM_BASE_URL + BOT_TOKEN + "/sendMessage", sendMessage, ResultModel.class);
        System.out.println(object);

    }

    public void whenAdmin(Update update) {
        String message = "Admin profili uchun kodni quyidagi tartibda kiriting:\n KOD=siznin_kodingiz";
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), message);
        restTemplate.postForObject(TELEGRAM_BASE_URL + BOT_TOKEN + "/sendMessage", sendMessage, ResultModel.class);
    }

    public void whenCode(Update update) {
        String text = update.getMessage().getText();
        String message = text.equals(code) ? "Hush kelibsiz " : "KOD hato kiritildi";
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), message);
        restTemplate.postForObject(TELEGRAM_BASE_URL + BOT_TOKEN + "/sendMessage", sendMessage, ResultModel.class);

    }

    public void whenPoll(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setCallbackData("Hammaga salom");
        button.setText("greeting");

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setCallbackData("Nima gap");
        button1.setText("was sup");

        buttonList.add(button);
        buttonList.add(button1);
        list.add(buttonList);

        inlineKeyboardMarkup.setKeyboard(list);
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Sizga response");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        telegramFeign.sendMessageToUser("/bot" + BOT_TOKEN, sendMessage );
    }
}
