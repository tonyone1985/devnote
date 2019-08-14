package com.devnote.mapping;

import com.devnote.models.Filebean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FileMapper {
    @Select("SELECT id,title FROM files WHERE title like  '%'||#{t}||'%'")
    List<Filebean> selectFiles(String t);


    @Select("SELECT id,title,content FROM files WHERE id = #{id}")
    Filebean selectFile(int id);


    @Insert({"INSERT INTO files (title,content) values(#{title},#{content})"})
    int insert(Filebean note);
    @Delete({"DELETE FROM files WHERE id=#{id}"})
    int del(int id);

}
