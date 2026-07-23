package epic_energy.BW5_Team3.security;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.exceptions.UnauthorizedException;
import epic_energy.BW5_Team3.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Por favor incluye el Token en el header Authorization con formato Bearer.");
            }

            String accessToken = authHeader.substring(7);

            // 1. Validate the token
            jwtTools.verifyToken(accessToken);

            // 2. Extract the employee ID and search the database
            String id = jwtTools.extractIdFromToken(accessToken);
            Employee currentEmployee = employeeService.findById(UUID.fromString(id));

            // 3. Map roles to Spring Security's GrantedAuthority
            List<SimpleGrantedAuthority> authorities = currentEmployee.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole()))
                    .toList();

            // 4. Map roles to Spring Security's GrantedAuthority
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentEmployee, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            // Map roles to Spring Security's GrantedAuthority
            resolver.resolveException(request, response, null, ex);
        }
    }

    // Ignorar el filtro para las rutas públicas de autenticación (/auth/**)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match("/auth/**", path) || matcher.match("/roles/**", path);
    }
}