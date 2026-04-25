package com.dailycodework.demoshops.controller;

import com.dailycodework.demoshops.dto.UserDto;
import com.dailycodework.demoshops.exceptions.AlreadyExistsException;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.request.CreateUserRequest;
import com.dailycodework.demoshops.request.UpdateRequest;
import com.dailycodework.demoshops.response.ApiResponse;
import com.dailycodework.demoshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {


    private final IUserService userService;


    @GetMapping("{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {

            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);

            return ResponseEntity.ok(new ApiResponse("User found Successfully!", userDto));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {

        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateRequest request,@PathVariable Long userId) {

        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user); // converting
            return ResponseEntity.ok(new ApiResponse("update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }


    }


    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(Long userId)
    {
        try {
            User user = userService.deleteUser(userId);
            //UserDto userDto = userService.convertUserToDto(user);

            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));

        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
