package com.gm.practicasya.services;

import com.gm.practicasya.entities.Role;
import com.gm.practicasya.entities.UserEntity;
import com.gm.practicasya.payloads.AuthCreateUserRequest;
import com.gm.practicasya.payloads.AuthLoginRequest;
import com.gm.practicasya.payloads.AuthResponse;
import com.gm.practicasya.repositories.RoleRepository;
import com.gm.practicasya.repositories.UserRepository;
import com.gm.practicasya.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthResponse createUser(AuthCreateUserRequest createRoleRequest) {

        String username = createRoleRequest.username();
        String password = createRoleRequest.password();
        List<String> rolesRequest = createRoleRequest.roleList();

        Set<Role> roleEntityList = new HashSet<>(roleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest));

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .firstName(createRoleRequest.firstName())
                .lastName(createRoleRequest.lastName())
                .phoneNumber(createRoleRequest.phoneNumber())
                .companyName(createRoleRequest.companyName())
                .companyRUC(createRoleRequest.companyRUC())
                .roles(roleEntityList)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .build();

        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponse(
                accessToken,
                userSaved.getId(),
                userSaved.getUsername(),
                userSaved.getRoles(),
                userSaved.getFirstName(),
                userSaved.getLastName(),
                userSaved.getPhoneNumber(),
                userSaved.getCompanyName(),
                userSaved.getCompanyRUC()
        );
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {

        String username = authLoginRequest.username();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username is not available"));

        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return new AuthResponse(
                accessToken,
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getRoles(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getPhoneNumber(),
                userEntity.getCompanyName(),
                userEntity.getCompanyRUC()
        );
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
