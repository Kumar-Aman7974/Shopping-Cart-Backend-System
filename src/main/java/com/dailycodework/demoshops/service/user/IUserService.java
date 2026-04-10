package com.dailycodework.demoshops.service.user;

import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.request.CreateUserRequest;
import com.dailycodework.demoshops.request.UpdateRequest;

public interface IUserService {


    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateRequest request, Long id);
    void deleteUser(Long userId);

}
