package com.example.backend.config.spring.security;

import com.example.backend.config.application.ApplicationContextProvider;
import com.example.backend.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenVerifyFilter extends OncePerRequestFilter {

    private static final Set<String> WHITE_LIST = Stream.of(
            "/api/user/login",
            "/api/student/user/register",
            "/api/teacher/user/register",
            "/api/admin/user/register"
    ).collect(Collectors.toSet());

    RestDetailsServiceImpl restDetailsService(){
        return ApplicationContextProvider.getBean(RestDetailsServiceImpl.class);
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 白名单
        if (WHITE_LIST.contains(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer")) {
            //如果携带错误的token，则给用户提示请登录！
            chain.doFilter(request, response);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        } else {
            //如果携带了正确格式的token要先得到token
            String token = header.replace("Bearer", "");
            //验证token是否正确
            if (JwtUtil.validateToken(token)) {
                String username = JwtUtil.getUsernameFromToken(token);

                //构建认证对象
                UserDetails userDetails = restDetailsService().loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // 将认证对象添加到安全上下文中
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        }
    }

}
