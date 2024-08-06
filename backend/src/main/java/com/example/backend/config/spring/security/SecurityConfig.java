package com.example.backend.config.spring.security;

import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * The type Form login web security configurer adapter.
     */
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final SystemConfig systemConfig;
        private final RestDetailsServiceImpl restDetailsService;
        private final RestAccessDeniedHandler restAccessDeniedHandler;
        private final LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;
        private final RestAuthenticationProvider restAuthenticationProvider;
        private final RestLogoutSuccessHandler restLogoutSuccessHandler;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(SystemConfig systemConfig, RestDetailsServiceImpl restDetailsService, RestAccessDeniedHandler restAccessDeniedHandler, LoginAuthenticationEntryPoint loginAuthenticationEntryPoint, RestAuthenticationProvider restAuthenticationProvider, AuthenticationConfiguration authenticationConfiguration, AuthenticationConfiguration authenticationConfiguration1, RestLogoutSuccessHandler restLogoutSuccessHandler) {
            this.systemConfig = systemConfig;
            this.restDetailsService = restDetailsService;
            this.restAccessDeniedHandler = restAccessDeniedHandler;
            this.loginAuthenticationEntryPoint = loginAuthenticationEntryPoint;
            this.restAuthenticationProvider = restAuthenticationProvider;
            this.restLogoutSuccessHandler = restLogoutSuccessHandler;
        }

        /**
         * @param http http
         * @throws Exception exception
         *                   csrf is the from submit get method
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.headers().frameOptions().disable();

            List<String> securityIgnoreUrls = systemConfig.getSecurityIgnoreUrls();
            String[] ignores = new String[securityIgnoreUrls.size()];
            http.csrf().disable().httpBasic()
                    .and().addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new TokenVerifyFilter(), TokenLoginFilter.class)
                    .exceptionHandling().authenticationEntryPoint(loginAuthenticationEntryPoint)
                    .and().authenticationProvider(restAuthenticationProvider)
                    .authorizeRequests()
                    .antMatchers(securityIgnoreUrls.toArray(ignores)).permitAll()
                    .antMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())
                    .antMatchers("/api/teacher/**").hasRole(RoleEnum.TEACHER.getName())
                    .antMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
                    .anyRequest().permitAll()
                    .and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                    .and().userDetailsService(restDetailsService)
                    .logout().logoutUrl("/api/user/logout").logoutSuccessHandler(restLogoutSuccessHandler).invalidateHttpSession(true).deleteCookies("JSESSIONID");
        }

        //忽略websocket拦截
        @Override
        public void configure(WebSecurity webSecurity) {
            webSecurity.ignoring().antMatchers(
                    "/api/chat/doc", "/api/ppt/progress"
            );
        }


        /**
         * Cors configuration source cors configuration source.
         *
         * @return the cors configuration source
         */
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setMaxAge(3600L);
            configuration.setAllowedOrigins(Collections.singletonList("*"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/api/**", configuration);
            return source;
        }

        /**
         * Authentication filter rest login authentication filter.
         *
         * @return the rest login authentication filter
         * @throws Exception the exception
         */
        @Bean
        public TokenLoginFilter authenticationFilter() throws Exception {
            TokenLoginFilter authenticationFilter = new TokenLoginFilter();
            authenticationFilter.setAuthenticationManager(authenticationManagerBean());
            return authenticationFilter;
        }
    }
}
