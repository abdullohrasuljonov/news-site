package uz.pdp.newssite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    @NotNull(message = "FullName must not be empty!")
    private String fullName;

    @NotNull(message = "Username must not be empty!")
    private String username;

    @NotNull(message = "Password must not be empty!")
    private String password;

    @NotNull(message = "Role must not be empty!")
    private Long roleId;

}
