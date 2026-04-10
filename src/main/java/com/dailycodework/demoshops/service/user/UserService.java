package com.dailycodework.demoshops.service.user;

import com.dailycodework.demoshops.exceptions.AlreadyExistsException;
import com.dailycodework.demoshops.exceptions.ResourceNotFoundException;
import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.repository.UserRepository;
import com.dailycodework.demoshops.request.CreateUserRequest;
import com.dailycodework.demoshops.request.UpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow( () -> new ResourceNotFoundException("User not found!"));

    }

    @Override
    public User createUser(CreateUserRequest request) {

        return Optional.of(request)
                .filter(user -> userRepository.existsByEmail(request.getEmail()))
                // now if the user does not exist , come here map
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);

                }).orElseThrow( () -> new AlreadyExistsException("Oops!" +   request.getEmail() +"already exists!"));

    }

    @Override
    public User updateUser(UpdateRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);

                }). orElseThrow(() -> new ResourceNotFoundException("User not Found!"));

    }

    @Override
    public void deleteUser(Long userId) {
        // first get the userID
         userRepository.findById(userId)
                 .ifPresentOrElse(userRepository::delete, () -> {
                     throw new ResourceNotFoundException("User not found!");
                 });

    }
}

