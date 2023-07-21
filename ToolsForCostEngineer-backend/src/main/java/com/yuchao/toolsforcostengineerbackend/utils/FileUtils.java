package com.yuchao.toolsforcostengineerbackend.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname FileUtils
 * @Description TODO
 * @Date 2023/7/18 23:30
 * @Created by YuChao
 * @Version 1.0
 */
public class FileUtils {
    public static File transferToFile(MultipartFile multipartFile) {
//        选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
//         File file = null;
        // try {
        //     String originalFilename = multipartFile.getOriginalFilename();
        //     String[] filename = originalFilename.split("\\.");
        //     file=File.createTempFile(filename[0], filename[1] + ".");
        //     multipartFile.transferTo(file);
        //     file.deleteOnExit();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        // return file;
        File file = new File("temp_" + multipartFile.getOriginalFilename());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }
        return file;
    }
}
