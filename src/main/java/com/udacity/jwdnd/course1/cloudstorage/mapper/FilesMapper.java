package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    Files findById(Integer fileid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    Files findFileByFileName(String filename);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<Files> findByUserId(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) VALUES (#{file.filename}, #{file.contenttype}, #{file.filesize}, #{file.filedata}, #{userid})")
    int saveFile(Files file, Integer userid);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    int deleteFile(Integer fileid);
}
