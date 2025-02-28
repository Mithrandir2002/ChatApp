package org.peppermint.ChatApp.configuration.securityconfig.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.configuration.securityconfig.SecurityConstants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private List<RequestMatcher> requestMatchers;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPermittedPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(SecurityConstants.JWT_HEADER);

        String token = header.replace(SecurityConstants.BEARER, "");

        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                new ArrayList<>()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean isPermittedPath(HttpServletRequest requestMatcher) {
        return requestMatchers.stream().anyMatch(matcher -> matcher.matches(requestMatcher));
    }
}
