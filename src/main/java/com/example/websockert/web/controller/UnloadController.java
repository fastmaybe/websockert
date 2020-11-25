package com.example.websockert.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @Author: liulang
 * @Date: 2020/9/23 16:22
 */
@RestController
public class UnloadController {


    @PostMapping("upload")
    public void upload( @RequestParam("file")MultipartFile file) throws IOException {

        File local = new File("G:\\", file.getOriginalFilename());
        String name = file.getName();
        String name1 = file.getOriginalFilename();
        file.transferTo(local);

    }

    @GetMapping("upload")
    public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //取出监听器MyProgress在session中保存的进度信息
        String progress=(String) req.getSession().getAttribute("progress");
        //响应
        resp.getWriter().print(progress);
    }
}
