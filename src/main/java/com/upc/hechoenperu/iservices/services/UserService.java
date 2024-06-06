package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.dtos.response.JwtResponse;
import com.upc.hechoenperu.dtos.response.UserByOffsetLimitResponseDTO;
import com.upc.hechoenperu.entities.Role;
import com.upc.hechoenperu.entities.User;
import com.upc.hechoenperu.iservices.IUserService;
import com.upc.hechoenperu.repositories.RoleRepository;
import com.upc.hechoenperu.repositories.UserRepository;
import com.upc.hechoenperu.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired // Inyecta el bean que implementa la interface UserRepository
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenProvider;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User insert(User user) {
        // If the email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("The email " + user.getEmail() + " already exists");
        }
        // If the email does not exist

        // encrypt the password
        String bcryptPassword = bcrypt.encode(user.getPassword());

        // set the encrypted password
        user.setPassword(bcryptPassword);

        // set the user with role USER
        var roles = roleRepository.findByNameRole(Role.NameRole.USER);

        // set the user with role USER
        user.setRoles((Collections.singleton(roles))); // establece un solo rol

        //
        return userRepository.save(user);
    }
    @Override
    public List<User> list() {
        return userRepository.list();
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User update(User user, String currentPassword, String newPassword) throws Exception {
        Optional<User> existingUserOptional = userRepository.findById(user.getId());
        if (existingUserOptional.isEmpty() || !existingUserOptional.get().getEnabled()) {
            throw new IllegalArgumentException("User " + user.getId() + " not found");
        }

        User existingUser = existingUserOptional.get();

        // Check if passwords are provided and match
        if (currentPassword != null && !currentPassword.isBlank() &&
                newPassword != null && !newPassword.isBlank()) {
            if (!bcrypt.matches(currentPassword, existingUser.getPassword())) {
                throw new IllegalArgumentException("Current password is incorrect");
            }
            existingUser.setPassword(bcrypt.encode(newPassword));
        }

        // Update the name and last name
        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());

        return userRepository.save(existingUser);
    }

    @Override
    public User searchId(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws Exception{
        User userinactive = searchId(id);
        userinactive.setEnabled(false);
        userRepository.save(userinactive);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JwtResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (!user.getEnabled()) {
            //throw new RuntimeException("User is not enabled");
            throw new IllegalArgumentException("User " + user.getEmail() + " is not enabled");
        }
        try {
            authenticate(email, password);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String token = jwtTokenProvider.generateToken(userDetails);
        return new JwtResponse(token);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
    @Override
    public User findUserById(Long id) {
        if (userRepository.findUserById(id) == null
                || !userRepository.findUserById(id).getEnabled()) {
            throw new IllegalArgumentException("User not found"); // If user is not found or is not enabled
        }
        return userRepository.findUserById(id);
    }

    @Override
    public List<User> listUsersByPageModeAdmin(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return userRepository.listUsersByPageModeAdmin(pageable);
    }
}
