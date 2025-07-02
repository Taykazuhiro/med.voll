package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//Classe OncePerRequestFilter é a classe spring que garante que o filtro vai ser executado 1x para cada requisição
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if (tokenJWT != null) {
            // se o token não estiver nulo, pega o subject do token (email)
            var subject = tokenService.getSubject(tokenJWT);
            //autenticação forçada
            //pega o OBJETO login do usuario que está no banco
            var usuario = repository.findByLogin(subject);
            //cria um objeto do tipo UsernamePasswordAuthenticationToken que precisa dos parâmetros
            //Objeto principal(usuário), credencial (que não precisa pois já estamos passando o usuário, e o tipo de autorização que vem do objeto usuário.
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            //estou falando para o spring que esse usuário que estou informando entre os parenteses está autenticado CONFIA NA MÃE SPRING
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);

    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
