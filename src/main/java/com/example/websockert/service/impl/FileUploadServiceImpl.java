package com.example.websockert.service.impl;

import com.example.websockert.model.vo.ResponseJson;
import com.example.websockert.service.FileUploadService;
import com.example.websockert.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @Author: liulang
 * @Date: 2020/9/18 18:17
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final static String SERVER_URL_PREFIX = "http://localhost:8080/WebSocket/";
    private final static String FILE_STORE_PATH = "UploadFile";

    @Override
    public ResponseJson upload(MultipartFile file, HttpServletRequest request) {
        //重命名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFilename = file.getOriginalFilename();
        String size = FileUtils.getFormatSize(file.getSize());
        String suffix = "";
        if (originalFilename.contains(".")){
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        fileName = fileName+suffix;

        String prefix = request.getSession().getServletContext().getRealPath("/") + FILE_STORE_PATH;
        System.out.println("存储路径为:" + prefix + "\\" + fileName);
        Path filePath = Paths.get(prefix, fileName);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseJson().error("文件上传发生错误！");
        }
        return new ResponseJson().success()
                .setData("originalFilename", originalFilename)
                .setData("fileSize", fileName)
                .setData("fileUrl", SERVER_URL_PREFIX + FILE_STORE_PATH + "\\" + fileName);
    }
}
