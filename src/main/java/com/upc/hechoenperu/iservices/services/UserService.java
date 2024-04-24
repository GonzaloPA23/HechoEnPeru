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
        // si el email ya existe
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email " + user.getEmail() + " already exists");
        }
        // Si el email no existe se

        // encripta la contraseña
        String bcryptPassword = bcrypt.encode(user.getPassword());

        // se establece la contraseña encriptada
        user.setPassword(bcryptPassword);

        // se establece el rol por defecto de USER
        var roles = roleRepository.findByNameRole(Role.NameRole.USER);

        // se establece el rol del usuario
        user.setRoles((Collections.singleton(roles))); // establece un solo rol

        // se guarda el usuario Y se retorna
        return userRepository.save(user);
    }
    @Override
    public List<User> list() {
        return userRepository.findAll();
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
        searchId(user.getId());
        return userRepository.save(user);
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
}
