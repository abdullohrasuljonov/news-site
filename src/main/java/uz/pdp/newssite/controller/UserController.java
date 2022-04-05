package uz.pdp.newssite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.newssite.entity.User;
import uz.pdp.newssite.payload.ApiResponse;
import uz.pdp.newssite.payload.UserDto;
import uz.pdp.newssite.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize(value = "hasAnyAuthority(ADD_USER)")
    @PostMapping
    public HttpEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(DELETE_MY_ACCOUNT)")
    @DeleteMapping("/deleteMyAccount")
    public ResponseEntity<?> deleteMyAccount() {
        ApiResponse apiResponse = userService.deleteMyAccount();
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(DELETE_USER)")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        ApiResponse apiResponse = userService.deleteUser(username);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(EDIT_USER)")
    @PutMapping("/editUser")
    public ResponseEntity<?> editUser(@RequestParam String username, @Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.editUser(username, userDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(VIEW_USERS)")
    @GetMapping("/{page}")
    public HttpEntity<?> viewUsers(Integer page) {
        Page<User> users = userService.viewUser(page);
        return ResponseEntity.ok(users);
    }

}
