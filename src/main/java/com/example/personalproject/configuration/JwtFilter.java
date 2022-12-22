package com.example.personalproject.configuration;

import com.example.personalproject.JwtTokenUtil.JwtTokenUtil;
import com.example.personalproject.exception.ErrorCode;
import com.example.personalproject.exception.UserException;
import com.example.personalproject.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final String secretKey;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(AUTHORIZATION);
        if(authorization == null || !authorization.startsWith("Bearer ")){
            log.error("Token 이 없거나 잘못되었습니다.");
            filterChain.doFilter(request,response);
        return;
        }

        String token;

        try {
            token = authorization.split(" ")[1];

        } catch (Exception e) {
            log.error("token 추출에 실패하였습니다.");
            filterChain.doFilter(request,response);
            return;
        }

        if(JwtTokenUtil.isExpired(token,secretKey)){
            log.error("Token 이 만료되었습니다.");
            filterChain.doFilter(request,response);
            return;
        }

        String userName = JwtTokenUtil.openToken(token,secretKey).get("userName", String.class);
        log.info("userName:{}", userName);


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }
}
