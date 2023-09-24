package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFiler extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //este es el filtro que yo implemente
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //con el metodo el filtro ya esta siendo llamado y puede
        // System.out.println("el filtro esta siendo llamado");
        //debe llamar al sgte filtro
        //obtener el token por los headers
        //System.out.println("este es el inicio del filtro ");
        var authHeader = request.getHeader("Authorization");
        //System.out.println(token);
        if (authHeader != null){
            //System.out.println("validamos que el token no es null");
            //System.out.println(token);
            //System.out.println(tokenService.getSubject(token));
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token); //extract username
            if (nombreUsuario != null ){
                //token valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);

                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());//forzamos el inicio de sesion
                //invocar a una clase de spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
    /*
    * $2y$10$xDn/sghuuiBLZ64zXNk1q.nkWGB85c3gETA7tq5pq6VfAvGq9yvqO por default bcryp
    * */
    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtenemos el token del encabezado "Authorization" de la solicitud
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extraemos el token del encabezado
            var token = authHeader.replace("Bearer ", "");
            // Obtenemos el nombre de usuario a partir del token
            var nombreUsuario = tokenService.getSubject(token);

            if (nombreUsuario != null) {
                // Token válido: encontramos un nombre de usuario en el token
                // Buscamos el usuario en la base de datos
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                // Creamos una autenticación de Spring Security y la configuramos
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                // Establecemos la autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Token expirado o inválido
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("El token JWT es inválido o ha expirado.");
                return; // Salimos del filtro para evitar procesar más la solicitud
            }
        }

        // Continuamos con la cadena de filtros
        filterChain.doFilter(request, response);
    }*/
}
