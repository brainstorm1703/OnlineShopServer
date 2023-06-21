package ua.chistikov.OnlineShopServer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.chistikov.OnlineShopServer.entity.User;
import ua.chistikov.OnlineShopServer.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OnlineShopUserDetailsService implements UserDetailsService {
    public static final Logger LOG = LoggerFactory.getLogger(OnlineShopUserDetailsService.class);
    private final UserRepository userRepository;

    @Autowired
    public OnlineShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return build(user);
    }

    public User loadUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }


    public static User build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}
