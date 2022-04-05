package uz.pdp.newssite.service;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.newssite.entity.Role;
import uz.pdp.newssite.entity.User;
import uz.pdp.newssite.payload.ApiResponse;
import uz.pdp.newssite.payload.UserDto;
import uz.pdp.newssite.repository.RoleRepository;
import uz.pdp.newssite.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse addUser(UserDto userDto){
        boolean existsByUsername = userRepository.existsByUsername(userDto.getUsername());
        if (existsByUsername)
            return new ApiResponse("Username already exist!", false);
        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (!optionalRole.isPresent())
            return new ApiResponse("Role not found!", false);
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(optionalRole.get());
        user.setFullName(userDto.getFullName());
        userRepository.save(user);
        return new ApiResponse("Successfully saved!", false);

    }

    public ApiResponse deleteMyAccount() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userRepository.deleteById(user.getId());
            return new ApiResponse("Successfully deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }
    }


    public ApiResponse deleteUser(String username) {
        try {
            Optional<User> byUsername = userRepository.findByUsername(username);
            if (!byUsername.isPresent()) return new ApiResponse("User not found!", false);
            User user = byUsername.get();
            userRepository.deleteById(user.getId());
            return new ApiResponse("Successfully deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }

    }

    public ApiResponse editUser(String username, UserDto userDto) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (!byUsername.isPresent()) return new ApiResponse("User not found!", false);
        Optional<Role> roleRepositoryById = roleRepository.findById(userDto.getRoleId());
        if (!roleRepositoryById.isPresent()) return new ApiResponse("Role not found!", false);

        User user = byUsername.get();
        boolean existsByUsernameAndIdNot = userRepository.existsByUsernameAndIdNot(userDto.getUsername(), user.getId());
        if (existsByUsernameAndIdNot) return new ApiResponse("Username already exists!", false);
        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepositoryById.get());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return new ApiResponse("User successfully edited!", true);


    }

    public Page<User> viewUser(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return userRepository.findAll(pageable);
    }
}
