package com.upc.hechoenperu.iservices.services;

import com.upc.hechoenperu.dtos.response.JwtResponse;
import com.upc.hechoenperu.entities.Role;
import com.upc.hechoenperu.entities.User;
import com.upc.hechoenperu.iservices.IUserService;
import com.upc.hechoenperu.repositories.RoleRepository;
import com.upc.hechoenperu.repositories.UserRepository;
import com.upc.hechoenperu.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
    public User insert(User user) {
        // If the email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email " + user.getEmail() + " already exists");
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
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public User update(User user) throws Exception {
        // si el usuario no existe o tiene el enabled en false lanza una excepción
        if (userRepository.findById(user.getId()).isEmpty() || !userRepository.findById(user.getId()).get().getEnabled()) {
            throw new Exception("User not found");
        }
        // Update the name, last name and password
        User userUpdate = searchId(user.getId());
        userUpdate.setName(user.getName());
        userUpdate.setLastName(user.getLastName());
        userUpdate.setPassword(bcrypt.encode(user.getPassword()));
        return userRepository.save(userUpdate);
    }
    @Override
    public User searchId(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }
    @Override
    public void delete(Long id) throws Exception{
        User userinactive = searchId(id);
        userinactive.setEnabled(false);
        userRepository.save(userinactive);
    }
    @Override
    public JwtResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (!user.getEnabled()) {
            throw new RuntimeException("User is not enabled");
        }
        try {
            authenticate(email, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String token = jwtTokenProvider.generateToken(userDetails);
        return new JwtResponse(token);
    }
    @Override
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
            throw new RuntimeException("User not found"); // If user is not found or is not enabled
        }
        return userRepository.findUserById(id);
    }
}
