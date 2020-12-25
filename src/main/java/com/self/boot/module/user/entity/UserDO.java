package com.self.boot.module.user.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserDO {
    private Long id;

    private String username;

    private String password;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer isDelete;
}
