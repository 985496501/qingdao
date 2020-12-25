package com.self.boot.module.user.service;

import com.self.boot.module.user.entity.UserDO;

import java.util.List;

public interface UserService {
    List<UserDO> listUsers();
}
