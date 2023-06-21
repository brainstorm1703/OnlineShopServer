package ua.chistikov.OnlineShopServer.facade;

import org.springframework.stereotype.Component;
import ua.chistikov.OnlineShopServer.dto.UserDTO;
import ua.chistikov.OnlineShopServer.entity.User;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }

}
