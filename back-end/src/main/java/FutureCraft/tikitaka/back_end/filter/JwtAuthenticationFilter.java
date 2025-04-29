package FutureCraft.tikitaka.back_end.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import FutureCraft.tikitaka.back_end.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            try {
                String token = parseBearerToken(request);
                System.out.println("토큰 : " + token);
                if (token == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
                String id = jwtProvider.validate(token);
                System.out.println("아이디 : " + id);
                if (id == null) {
                    filterChain.doFilter(request, response);
                    return; 
                }
                
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);            
                filterChain.doFilter(request, response);
                
            } catch (Exception e) {
                e.printStackTrace();
            }   
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        if (authorization.startsWith("Bearer")) {
            return authorization.substring(7);  
        }
        return null;
    }
}
