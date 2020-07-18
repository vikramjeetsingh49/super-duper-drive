package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/note/add")
    public String saveNote(Authentication authentication, Notes note) {
        User user = (User) authentication.getPrincipal();

        if (!ObjectUtils.isEmpty(note.getNoteid())) {
            notesService.updateNote(note);
        } else {
            notesService.saveNote(note, user.getUserid());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam("id") Integer noteid) {
        if (noteid > 0) {
            notesService.deleteNote(noteid);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
