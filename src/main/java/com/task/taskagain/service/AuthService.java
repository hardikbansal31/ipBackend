//package com.task.taskagain.service;
//
//import com.task.taskagain.model.User;
//import com.task.taskagain.model.Role;
//import com.task.taskagain.repository.UserRepository;
//import com.task.taskagain.security.JwtUtil;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//public class AuthService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
//
//    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
//                       JwtUtil jwtUtil, AuthenticationManager authenticationManager,
//                       UserDetailsService userDetailsService) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//    }
//
//    public String registerUser(String username, String password, boolean isAdmin) {
//        if (userRepository.findByUsername(username).isPresent()) {
//            throw new RuntimeException("Username already taken");
//        }
//
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole(isAdmin ? Role.ADMIN : Role.USER);
//        userRepository.save(user);
//
//        return jwtUtil.generateToken((UserDetails) user);
//    }
//
//    public String loginUser(String username, String password) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//
//        UserDetailsService userDetails = (UserDetailsService) userDetailsService.loadUserByUsername(username);
//        return jwtUtil.generateToken((UserDetails) userDetails);
//    }
//}

package com.task.taskagain.service;

import com.task.taskagain.model.User;
import com.task.taskagain.model.Role;
import com.task.taskagain.repository.UserRepository;
import com.task.taskagain.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String registerUser(String username, String password, boolean isAdmin) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(isAdmin ? Role.ADMIN : Role.USER);
        userRepository.save(user);

        return jwtUtil.generateToken(user); // ✅ No type casting needed
    }

    public String loginUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(user); // ✅ No type casting needed
    }
}
