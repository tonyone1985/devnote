package com.devnote.mapping;

import com.devnote.models.Filebean;
import com.devnote.models.Notebean;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface NoteMapper {

    @Select("SELECT * FROM notes WHERE title like  '%'||#{t}||'%'")
    List<Notebean> selectNotes(String t);

    @Update({"UPDATE notes set title=#{title},content=#{content} WHERE id=#{id}"})
    int update(Notebean note);
    @Insert({"INSERT INTO notes (title,content) values(#{title},#{content})"})
    int insert(Notebean note);
    @Delete({"DELETE FROM notes WHERE id=#{id}"})
    int del(int id);

}
