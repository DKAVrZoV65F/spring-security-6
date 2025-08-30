package org.spring.security6.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.spring.security6.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // очень простой кеш. В прод: Caffeine/Redis с TTL.
    private final Map<String, UserDetails> userCache = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        // 1) быстрый попыточный парсинг / валидация подписи и expiry (без DB)
        String userName;
        try {
            userName = jwtService.extractUserName(jwt);
        } catch (Exception e) {
            // подпись невалидна, или токен повреждён — пропускаем и не делаем DB-запрос
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userName != null && authentication == null) {
            // Authenticate

            // 2) попытка взять UserDetails из кеша
            UserDetails userDetails = userCache.get(userName);
            if (userDetails == null) {
                long t0 = System.currentTimeMillis();
                userDetails = userDetailsService.loadUserByUsername(userName);
                long t1 = System.currentTimeMillis();
                // debug: сколько занял DB-запрос
                logger.debug("Loaded user " + userName + " in " + (t1 - t0) + "ms");
                userCache.put(userName, userDetails); // в prod — TTL
            }

            // 3) окончательная проверка token<->user (включает expiry)
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
