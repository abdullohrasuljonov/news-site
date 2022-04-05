package uz.pdp.newssite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.newssite.entity.User;
import uz.pdp.newssite.exceptions.ResourceNotFoundException;
import uz.pdp.newssite.payload.ApiResponse;
import uz.pdp.newssite.payload.RegisterDto;
import uz.pdp.newssite.repository.RoleRepository;
import uz.pdp.newssite.repository.UserRepository;
import uz.pdp.newssite.utils.Constants;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegisterDto registerDto) {

        if (!registerDto.getPassword().equals(registerDto.getPrePassword()))
            return new ApiResponse("Passwords are not compatible!", false);

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ApiResponse("Username already exist!", false);
        }

        User user = new User(registerDto.getFullName(),
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                roleRepository.findByName(Constants.USER).orElseThrow(() -> new ResourceNotFoundException("role","name",Constants.USER)),
                true
                );
        userRepository.save(user);
        return new ApiResponse("Successfully registered!",true);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }


}
