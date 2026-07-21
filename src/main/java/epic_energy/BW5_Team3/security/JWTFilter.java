package epic_energy.BW5_Team3.security;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.exceptions.UnauthorizedException;
import epic_energy.BW5_Team3.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Please include the Token in Authorization with Bearer format.");
        }

        String accessToken = authHeader.substring(7);

        // 1. Validate Token
        jwtTools.verifyToken(accessToken);

        // 2. Extract the employee id and search in DB
        String id = jwtTools.extractIdFromToken(accessToken);
        Employee currentEmployee = employeeService.findById(UUID.fromString(id));

        // 3. Maped the roles GrantedAuthority to Spring
        List<SimpleGrantedAuthority> authorities = currentEmployee.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .toList();

        // 4. Authenticate
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentEmployee, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    // Ignore the filter for the public routes to Authentication (/auth/**)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}