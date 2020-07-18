package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FilesService {

    @Autowired
    private FilesMapper filesMapper;

    public List<Files> getAllFiles(Integer userId) {
        return filesMapper.findByUserId(userId);
    }

    public void saveFile(MultipartFile multipartFile, Integer userid) throws IOException {
        Files file = new Files();
        try {
            file.setContenttype(multipartFile.getContentType());
            file.setFiledata(multipartFile.getBytes());
            file.setFilename(multipartFile.getOriginalFilename());
            file.setFilesize(Long.toString(multipartFile.getSize()));
        } catch (IOException e) {
            throw e;
        }
        filesMapper.saveFile(file, userid);
    }

    public void deleteFile(int fileid) {
        filesMapper.deleteFile(fileid);
    }

    public Files getById(Integer fileId) {
        return filesMapper.findById(fileId);
    }

    public Files findFileByFileName(String filename) {
        return filesMapper.findFileByFileName(filename);
    }
}
