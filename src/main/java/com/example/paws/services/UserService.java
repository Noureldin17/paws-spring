package com.example.paws.services;

import com.example.paws.dao.UserRepository;
import com.example.paws.dto.UserProfileDTO;
import com.example.paws.entities.User;
import com.example.paws.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper userProfileMapper;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
            }
        };
    }

    public User getUserByEmail(String userEmail){
        return userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
    }
    public User save(User newUser){
        return userRepository.save(newUser);
    }

    public UserProfileDTO getUserProfileInfo(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        UserProfileDTO userProfileDTO = userProfileMapper.toUserProfileDTO(user);
        return userProfileDTO;
    }
}
