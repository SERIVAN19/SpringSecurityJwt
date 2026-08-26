package spring_security_jwt.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    //Dependencias necesarias para validar el token JWT y cargar los detalles del usuario
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Aquí iría la lógica para validar el token JWT y autenticar al usuario
        // Por ejemplo, podrías extraer el token del encabezado Authorization,
        // validarlo y, si es válido, establecer la autenticación en el contexto de seguridad.

        //Obtener el encabezado Authorization de la solicitud
        String authHeader = request.getHeader("Authorization");

        //validar header
        if(authHeader == null ||
                !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);// Devolvemos a la cadena de filtros el control
            return;
        }

        //Extraer el token
        String jwt = authHeader.substring(7);

        //Extaer el email del ususario
        String username = jwtService.extractUsername(jwt);

        // Validar si no hay autenticacion aún
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //User user = userRepository.findByEmail(userEmail).orElse(null);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.isTokenValid(jwt,userDetails)){
                // Si el token es válido, se puede establecer la autenticación en el contexto de seguridad
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Establecer detalles de la solicitud en el token de autenticación
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);// Establecer la autenticación en el contexto de seguridad
            }
        }

        filterChain.doFilter(request, response);
    }
}
