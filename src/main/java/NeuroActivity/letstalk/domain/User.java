package NeuroActivity.letstalk.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr1")
@ToString(of = {"id", "username"})
@EqualsAndHashCode(of = {"id"})
@Data
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue
    @JsonView(Views.IdText.class)
    private Long id;
    @Column(unique = true)
    @JsonView(Views.IdText.class)
    private String username;
    @JsonView(Views.IdText.class)
    private String userpic;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-mm-yyyy")
    private LocalDateTime lastVisit;

    @ElementCollection(targetClass = UsersRoles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UsersRoles> roles;

    public User(){}

    public Set<UsersRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UsersRoles> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}