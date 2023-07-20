package com.example.webhookdemo.service;

import com.example.webhookdemo.feign.TelegramFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.webhookdemo.message.RestConstanta.BOT_TOKEN;

@Service
@RequiredArgsConstructor
public class TelegramService {

    private final TelegramFeign telegramFeign;
    private final WebhookService webhookService;

    public void analyzerMessage(Update update){

        if(update.hasMessage()){
            String text = update.getMessage().getText();


            if(text.startsWith("KOD"))webhookService.whenCode(update);
            switch (text){
                case "/start" : webhookService.whenStart(update) ; break;
                case "/services": webhookService.whenService(update) ; break;
                case "/admin" : webhookService.whenAdmin(update);break;

                case "/poll" : webhookService.whenPoll(update);


            }


            if(update.hasCallbackQuery()){
                String data = update.getCallbackQuery().getData();
                String message;
                if(data.startsWith("Nima")){
                    message = "Tinch";
                }else if(data.startsWith("Hammage")){
                    message = "Okam salom sizgayam";
                }else message = "It seems smt went wrong";

                SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),message);
                telegramFeign.sendMessageToUser("/bot" + BOT_TOKEN, sendMessage );


            }
        }

    }


    public String switchCase(String res){

        return switch (res){
            case "Spring" ->  "Hello Spring" ;
            case "Summer" -> "Hello Summer" ;
            case "Authom " -> "Salom Kuz";
            default -> "Hech vaho yoq";
        };
    }


}
