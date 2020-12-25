package com.self.boot.module.user.service.impl;

import com.self.boot.module.user.entity.UserDO;
import com.self.boot.module.user.mapper.UserMapper;
import com.self.boot.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public List<UserDO> listUsers() {
        return userMapper.selectList();
    }
}
