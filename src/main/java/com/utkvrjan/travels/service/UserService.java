package com.utkvrjan.travels.service;

import com.utkvrjan.travels.entity.User;

public interface UserService {



    User login(User user);

    void register(User user);
}
