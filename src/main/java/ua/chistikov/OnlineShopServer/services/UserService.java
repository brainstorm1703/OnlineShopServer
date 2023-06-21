package ua.chistikov.OnlineShopServer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.chistikov.OnlineShopServer.dto.UserDTO;
import ua.chistikov.OnlineShopServer.entity.User;
import ua.chistikov.OnlineShopServer.entity.enums.ERole;
import ua.chistikov.OnlineShopServer.exceptions.NotAllowedException;
import ua.chistikov.OnlineShopServer.exceptions.UserExistException;
import ua.chistikov.OnlineShopServer.payload.request.SignupRequest;
import ua.chistikov.OnlineShopServer.repository.UserRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);
        try{
            LOG.info("Saving user {}", signupRequest.getUsername());
            return userRepository.save(user);
        } catch (Exception ex) {
            LOG.error("Error during registration. {}", ex.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }

    public List<User> getAllUsers(Principal principal){
        isUserAllowed(principal);
        return userRepository.findAll();
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        return userRepository.findUserByUsername(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username " + principal.getName()));
    }

    public void deleteUser(Long userId, Principal principal) {
        isUserAllowed(principal);
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User with this i"+ id + "not found"));
    }

    public void isUserAllowed(Principal principal){
        User user = getUserByPrincipal(principal);
        if (!user.getRoles().contains(ERole.ROLE_ADMIN)) {
            throw new NotAllowedException("You dont have permission to do that.");
        }
    }
}

