package kapsalon.nl.config;


import kapsalon.nl.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final UserDetailsService userDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(userDetailsService);
        return new ProviderManager(auth);
    }


    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                //Dienst
                .requestMatchers(HttpMethod.GET,"/api/v1/diensten").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.GET,"/api/v1/diensten/{id}").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.POST,"/api/v1/diensten").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.PUT,"/api/v1/diensten/{id}").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/diensten/{id}").hasAnyRole("OWNER")
                                // Barber
                .requestMatchers(HttpMethod.GET,"/api/v1/barbers").hasAnyRole("ADMIN","OWNER")
                .requestMatchers(HttpMethod.GET,"/api/v1/barbers/{id}").hasAnyRole("ADMIN","OWNER")
                .requestMatchers(HttpMethod.POST,"/api/v1/barbers").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.PUT,"/api/v1/barbers/{id}").hasAnyRole("OWNER","BARBER")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/barbers/{id}").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.GET,"/api/v1/barbers/available").hasAnyRole("CUSTOMER","OWNER")
                                // Image upload an download
                .requestMatchers(HttpMethod.POST,"/api/v1/images").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.GET,"/api/v1/images/{id}").hasAnyRole("OWNER")
                                // Kapsalon
                .requestMatchers(HttpMethod.GET,"/api/v1/kapsalon").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.GET,"/api/v1/kapsalon/{id}").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.POST,"/api/v1/kapsalon").hasAnyRole("CUSTOMER","OWNER")
                .requestMatchers(HttpMethod.PUT,"/api/v1/kapsalon/{id}").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/kapsalon/{id}").hasAnyRole("OWNER")
                                // Appointment
                .requestMatchers(HttpMethod.GET,"/api/v1/appointment").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.GET,"/api/v1/appointment/{id}").hasAnyRole("OWNER")
                .requestMatchers(HttpMethod.POST,"/api/v1/appointment").hasAnyRole("CUSTOMER")
                .requestMatchers(HttpMethod.PUT,"/api/v1/appointment/{id}").hasAnyRole("CUSTOMER")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/appointment/{id}").hasAnyRole("BARBER","CUSTOMER")
                .requestMatchers(HttpMethod.GET, "/api/v1/appointment/{id}/download-pdf").hasAnyRole("OWNER", "CUSTOMER")
                                // User
                .requestMatchers(HttpMethod.GET,"/api/v1/users").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/users/{username}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/v1/users/{username}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/users/{username}").hasAnyRole("ADMIN")
                                // Request rol
                .requestMatchers(HttpMethod.GET,"/api/v1/request-owner-role").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/request-owner-role/{id}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/v1/request-owner-role").hasAnyRole("CUSTOMER")
                .requestMatchers(HttpMethod.PUT,"/api/v1/request-owner-role/{id}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/request-owner-role/{id}").hasAnyRole("ADMIN")
                                //Role
                .requestMatchers(HttpMethod.POST,"/api/v1/users/{username}/authorities").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/users/{username}/authorities").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/users/{username}/authorities/{authority}").hasAnyRole("ADMIN")
                                //authe
                .requestMatchers(HttpMethod.POST,"/api/v1/users/register").permitAll()
                .requestMatchers("/api/v1/login").permitAll()
                .requestMatchers("/api/v1/authenticated").authenticated()


                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}