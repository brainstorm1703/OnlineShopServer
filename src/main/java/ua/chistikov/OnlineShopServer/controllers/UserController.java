package ua.chistikov.OnlineShopServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.chistikov.OnlineShopServer.dto.UserDTO;
import ua.chistikov.OnlineShopServer.entity.User;
import ua.chistikov.OnlineShopServer.facade.UserFacade;
import ua.chistikov.OnlineShopServer.payload.response.MessageResponse;
import ua.chistikov.OnlineShopServer.services.UserService;
import ua.chistikov.OnlineShopServer.validations.ResponseErrorValidation;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@CrossOrigin()
public class UserController {
    private final UserService userService;
    private final UserFacade userFacade;

    @Autowired
    public UserController(UserService userService, UserFacade userFacade, ResponseErrorValidation responseErrorValidation) {
        this.userService = userService;
        this.userFacade = userFacade;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUser(Principal principal) {
        List<UserDTO> userDTOList = userService.getAllUsers(principal)
                .stream()
                .map(userFacade::userToUserDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/{userId}/delete")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable("userId") String userId, Principal principal) {
        userService.deleteUser(Long.parseLong(userId), principal);
        return new ResponseEntity<>(new MessageResponse("user was deleted"), HttpStatus.OK);
    }

    @GetMapping("/role")
    public ResponseEntity<MessageResponse> getRole(Principal principal){
        User user = userService.getCurrentUser(principal);
        return new ResponseEntity<>(new MessageResponse(user.getRoles().iterator().next().toString()), HttpStatus.OK);
    }
}
