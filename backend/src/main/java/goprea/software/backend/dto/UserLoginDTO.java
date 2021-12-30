package goprea.software.backend.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserLoginDTO {
    @NotNull(message = "Username cannot be null!")
    private String username;

    @NotNull(message = "Password cannot be null!")
    private String password;
}
