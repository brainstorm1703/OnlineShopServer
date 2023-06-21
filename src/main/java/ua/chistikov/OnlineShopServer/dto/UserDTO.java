package ua.chistikov.OnlineShopServer.dto;

import lombok.Data;
import ua.chistikov.OnlineShopServer.entity.enums.ERole;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private Set<ERole> roles;
}
