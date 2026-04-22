package com.ver.ssms.model;

import com.ver.ssms.dto.auth.RegisterUser;
import com.ver.ssms.utility.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.HexFormat;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static UserEntity signUp(RegisterUser registerUser){
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUserId(newUserEntity.generateHexId());
        newUserEntity.setUsername(registerUser.getUsername());
        newUserEntity.setPassword(registerUser.getPassword());
        newUserEntity.setRole(UserRole.USER);
        return newUserEntity;
    }

    public String generateHexId() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[4]; // 4 bytes = 8 hex chars -> change this for prefered length
        random.nextBytes(bytes);

        // HexFormat is available in Java 17+
        return HexFormat.of().formatHex(bytes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {return UserDetails.super.isAccountNonExpired();}

    @Override
    public boolean isAccountNonLocked() {return UserDetails.super.isAccountNonLocked();}

    @Override
    public boolean isCredentialsNonExpired() {return UserDetails.super.isCredentialsNonExpired();}

    @Override
    public boolean isEnabled() {return UserDetails.super.isEnabled();}
}
