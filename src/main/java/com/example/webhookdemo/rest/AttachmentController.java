package com.example.webhookdemo.rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("api/attachment")
public class AttachmentController {

    // TODO poll, buttonlar ni ko'rib chiqish

    @GetMapping
    public void getFileByName(@RequestParam String fileName,
                              HttpServletResponse response){
        File file = new File("upload" + "/" + fileName);

        System.out.println(file.getName());
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "max-age=8640000");
        response.setHeader("Content-disposition", "attachment; fileName=\"" + file.getName() + "\"");
        try {
            InputStream in = new FileInputStream(file);
            FileCopyUtils.copy(in, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
