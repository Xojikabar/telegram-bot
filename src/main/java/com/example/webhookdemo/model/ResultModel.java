package com.example.webhookdemo.model;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.Message;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultModel {
    private boolean ok;
    private Message result;
}
