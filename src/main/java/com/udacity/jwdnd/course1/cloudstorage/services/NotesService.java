package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    @Autowired
    private NotesMapper notesMapper;

    public List<Notes> getAllNotes(Integer userid) {
        return notesMapper.findByUserId(userid);
    }

    public void saveNote(Notes note, int userid) {
        notesMapper.saveNote(note, userid);
    }

    public void updateNote(Notes note) {
        notesMapper.updateNote(note);
    }

    public void deleteNote(int noteid) {
        notesMapper.deleteNote(noteid);
    }
}
