package com.example.backend.config.spring.security;

import com.alibaba.fastjson.JSON;
import com.example.backend.base.SystemCode;
import com.example.backend.model.request.student.user.UserRegisterRequest;
import com.example.backend.service.UserService;
import com.example.backend.utils.JwtUtil;
import com.example.backend.utils.SpringContextUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public TokenLoginFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    private UserService userService(){
        return SpringContextUtil.getBean(UserService.class);
    }

    public String getJson(HttpServletRequest request) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            sb.append(inputStr);
        }
        return sb.toString();
    }

    /**
     * 具体认证的方法
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        UserRegisterRequest user = null;
        try {
            user = JSON.parseObject(getJson(request), UserRegisterRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        this.setDetails(request, authRequest);
        return authenticationManager.authenticate(authRequest);
    }

    /**
     * 登录成功后的处理
     *
     * @param request
     * @param response
     * @param chain
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain chain
            , Authentication authentication) throws IOException, ServletException {
        // 生成Token信息
        String token = JwtUtil.generateToken(authentication.getName());
        response.addHeader("Authorization", "Bearer" + token);

        Object object = authentication.getPrincipal();
        if (null != object) {
            User springUser = (User) object;
            com.example.backend.model.entity.User user = userService().getUserByUserName(springUser.getUsername());
            if (null != object) {
                com.example.backend.model.entity.User newUser = new com.example.backend.model.entity.User();
                newUser.setUserName(user.getUserName());
                newUser.setImagePath(user.getImagePath());
                RestUtil.response(response, SystemCode.OK.getCode(), SystemCode.OK.getMessage(), newUser);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                RestUtil.response(response, SystemCode.UNAUTHORIZED.getCode(), SystemCode.UNAUTHORIZED.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        try {
            RestUtil.response(response, SystemCode.AuthError.getCode(), exception.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        } catch (Exception outEx) {
            outEx.printStackTrace();
        }
    }


}
