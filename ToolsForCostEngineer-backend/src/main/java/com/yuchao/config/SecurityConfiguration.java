package com.yuchao.config;

import com.alibaba.fastjson.JSONObject;
import com.yuchao.entity.RestBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * @ClassName SecurityConfiguration
 * @Description TODO
 * @Author YuChao
 * @Date 2023/7/16 2:16
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
        public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("api/auth/login")
                .successHandler(this::onAuthenticationSuccess)
    }

        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(JSONObject.toJSONString(RestBean.success("登录成功")));
        }
}
