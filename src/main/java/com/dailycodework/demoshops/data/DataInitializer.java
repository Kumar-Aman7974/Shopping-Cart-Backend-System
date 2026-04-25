package com.dailycodework.demoshops.data;

import com.dailycodework.demoshops.model.Role;
import com.dailycodework.demoshops.model.User;
import com.dailycodework.demoshops.repository.RolesRepository;
import com.dailycodework.demoshops.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer  implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RolesRepository rolesRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultUserIfNotExits();
        createDefaultAdminIfNotExits();


    }

    private void createDefaultUserIfNotExits() {
        Role userRole = rolesRepository.findByName("ROLE_USER");

        for(int i = 1; i <= 5; i++)
        {
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {

                continue;
            }

            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User"+ i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user "+ i + "created successfully.");
        }
    }

    private void createDefaultAdminIfNotExits() {

        Role adminRole = rolesRepository.findByName("ROLE_ADMIN");


        for(int i = 1; i <= 2; i++)
        {
            String defaultEmail = "admin"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {

                continue;
            }

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin"+ i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default  admin user "+ i + "created successfully.");
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles)
    {
        roles.stream()
                .filter(role -> rolesRepository.findByName(role) == null)
                .map(Role::new).forEach(rolesRepository::save);
    }
}
