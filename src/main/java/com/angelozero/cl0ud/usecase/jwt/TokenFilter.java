package com.angelozero.cl0ud.usecase.jwt;

import com.angelozero.cl0ud.exception.jwt.BearerTokenJwtAuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class TokenFilter extends GenericFilterBean {

    private final ResolveToken resolveToken;
    private final ValidateToken validateToken;
    private final GetAuthentication getAuthentication;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {

            String token = resolveToken.execute((HttpServletRequest) servletRequest);

            if (!StringUtils.isEmpty(token) && validateToken.execute(token)) {
                Authentication auth = getAuthentication.execute(token);
                if (ObjectUtils.isNotEmpty(auth)) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception ex) {
            throw new BearerTokenJwtAuthException("Could not filter the token");
        }
    }
}
