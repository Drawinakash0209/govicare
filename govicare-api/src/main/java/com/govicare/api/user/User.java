package com.govicare.api.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.govicare.api.crop.Crop;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data // Lombok: Generates getters, setters, toString, etc.
@Builder // Lombok: Implements the builder pattern.
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // Maps this class to the 'users' table.
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String location;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // Our 'username' is the email address.
        return email;
    }
    
    
    
    // Other UserDetails methods (isAccountNonExpired, etc.)
    // For our MVP, we can just return true.
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
		name = "user_crops",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "crop_id")
	)
    private Set<Crop> selectedCrops = new HashSet<>();

	// Add any additional methods or logic as needed.
}