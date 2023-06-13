package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.exception.BadApiRequest;
import com.lcwd.electronic.store.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * @implNote This Process is implementing for the Upload User Image
     * @param file
     * @param path
     * @return
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException
    {
        logger.info("Initiating dao call for the Upload User Image details");
        String originalFilename = file.getOriginalFilename();
        logger.info("File name : {}"+ originalFilename,file);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        logger.info("Uploaded image Name :{}"+extension);
        String fileNameWithExtension = filename + extension;
        String fullPathWitheFilename = path + fileNameWithExtension;

        if (extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".pnj")) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathWitheFilename));

            logger.info("Completed dao call for the Upload User Image details");
            return fileNameWithExtension;
        } else {
            throw new BadApiRequest("File With This" + extension + "Not Allowed !!", true);
        }

    }

    /**
     * @implNote This Process is Implementing for the get User Image
     * @param path
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException
    {
        logger.info("Initiating dao call for the get User Image details");
        String fullPath= path + name;
        InputStream inputStream= new FileInputStream(fullPath);
        logger.info("Completed dao call for the get User Image details");
        return inputStream;
    }
}
