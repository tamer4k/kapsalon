package kapsalon.nl.models.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kapsalon.nl.models.entity.Authority;
import org.springframework.validation.annotation.Validated;


import java.util.Set;

@Validated
public class UserDto {

    @NotBlank(message = "het mag niet leeg zijn")
    public String username;

    @NotBlank(message = "het mag niet leeg zijn")
    @Size(min = 4, max = 20, message = "Lengte moet tussen 2 en 20 liggen")
    public String password;

    @Email(message = "Ongeldig e-mailadres")
    public String email;
    public Set<Authority> authorities;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}