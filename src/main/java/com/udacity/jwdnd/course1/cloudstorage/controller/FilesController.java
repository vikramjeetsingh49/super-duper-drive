package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FilesController {

    @Autowired
    private FilesService filesService;

    @PostMapping("/file/upload")
    public String saveFile(Authentication authentication, MultipartFile fileUpload) throws IOException {
        User user = (User) authentication.getPrincipal();
        Files existingFile  = filesService.findFileByFileName(fileUpload.getOriginalFilename());

        if (!ObjectUtils.isEmpty(existingFile)) {
            return "redirect:/result?exists";
        }

        if (fileUpload.isEmpty()) {
            return "redirect:/result?error";
        }
        filesService.saveFile(fileUpload, user.getUserid());
        return "redirect:/result?success";
    }

    @GetMapping("/file/delete")
    public String deleteFile(@RequestParam("id") Integer fileid) {
        if (fileid > 0) {
            filesService.deleteFile(fileid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }

    @RequestMapping(value = "/file/download/{fileid}")
    public ResponseEntity<ByteArrayResource> downloadFile(HttpServletRequest request, @PathVariable("fileid") Integer fileid){
        Files files = filesService.getById(fileid);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + files.getFilename());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        ByteArrayResource resource = new ByteArrayResource(files.getFiledata());

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(Long.parseLong(files.getFilesize()))
                .contentType(MediaType.parseMediaType(files.getContenttype()))
                .body(resource);
    }
}
