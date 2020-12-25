package com.self.boot.module.user.mapper;

import com.self.boot.module.user.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<UserDO> selectList();
}
