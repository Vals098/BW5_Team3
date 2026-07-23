package epic_energy.BW5_Team3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
//    @Autowired
//    private JWTFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JWTFilter jwtFilter) throws Exception {

        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());



        httpSecurity.authorizeHttpRequests(req -> req
                // We allow /auth/** without a token and require a token for any other route
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/roles/**").permitAll()

//                .requestMatchers(HttpMethod.POST, "/invoices/statuses").hasAuthority("ADMIN")
//
//                .requestMatchers(HttpMethod.GET, "/client/invoices/**").hasAnyAuthority("USER", "ADMIN")
//                .requestMatchers(HttpMethod.POST, "/client/invoices/**").hasAnyAuthority("USER", "ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/client/invoices/**").hasAuthority("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/client/invoices/**").hasAuthority("ADMIN")

                .anyRequest().authenticated());
        // We added the JWTFilter before Spring's default filter
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    //    PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
