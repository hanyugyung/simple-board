package org.example.han.common.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.han.common.util.Utils;
import org.example.han.interfaces.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private String key;

    public JwtAuthFilter(String key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Claims tokenBody = JwtValidator.validateAndGetClaims(key, request.getHeader("Token"));

        if (tokenBody == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            Utils.getObjectMapper().writeValue(
                    response.getOutputStream(), CommonResponse.fail(CommonResponse.CustomErrorMessage.USER_FAIL_AUTHORIZATION));
        } else {

            TokenInfo tokenInfo = Utils
                    .getObjectMapper()
                    .convertValue(tokenBody.get("info"), TokenInfo.class);

            AccessUser user = new AccessUser(tokenInfo.id(), tokenInfo.loginId(), tokenInfo.userName(), tokenInfo.roles());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
