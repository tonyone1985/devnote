package com.devnote.mapping;

import com.devnote.models.TestModel;
import org.apache.ibatis.annotations.Select;

public interface Testmapper {

    @Select("SELECT * FROM address WHERE address_id = #{id}")
    TestModel selectTestModel(int id);
}
