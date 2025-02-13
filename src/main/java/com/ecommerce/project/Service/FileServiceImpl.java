package com.ecommerce.project.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File names of current / original file
        //這一行從上傳的檔案中獲取檔案的原始名稱。
        // 例如，如果使用者上傳了一個名為image.jpg的檔案，originalFilName將會是"image.jpg"。
        String originalFilName = file.getOriginalFilename();

        //Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        //mat.jpg --> 7578 -->7578.jpg
        //它從originalFilName中提取了副檔名（例如.jpg），然後將這個副檔名附加到隨機生成的ID後面。
        // 例如，假設隨機ID是7578，原始檔案是image.jpg，那麼fileName就會是7578.jpg。
        String fileName = randomId.concat(originalFilName.substring(originalFilName.lastIndexOf('.')));

        //這行用來檢查指定的path（存放檔案的目錄）是否存在。new File(path)會創建一個指向該目錄的File物件，但不會實際創建該目錄。
        //String filePath = path + File.pathSeparator + fileName;
        String filePath = path + File.separator + fileName;

        //Check if path exist and create
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();
        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
