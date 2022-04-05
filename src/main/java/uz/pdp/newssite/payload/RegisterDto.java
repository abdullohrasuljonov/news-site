package uz.pdp.newssite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotNull(message = "FullName must not be empty!")
    private String fullName;

    @NotNull(message = "Username must not be empty!")
    private String username;

    @NotNull(message = "Password must not be empty!")
    private String password;

    @NotNull(message = "PrePassword must not be empty!")
    private String prePassword;
}
