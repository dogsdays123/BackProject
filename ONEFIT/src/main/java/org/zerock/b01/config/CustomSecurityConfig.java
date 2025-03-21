package org.zerock.b01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.zerock.b01.security.CustomOAuth2UserService;
import org.zerock.b01.security.CustomUserDetailsService;
import org.zerock.b01.security.handler.Custom403Handler;
import org.zerock.b01.security.handler.CustomSocialLoginSuccessHandler;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        log.info("--------------------------------configure-----------------------------------");

        //일반 로그인
        http.formLogin(httpSecurityFormLoginConfigurer -> {
            httpSecurityFormLoginConfigurer
                    .loginPage("/login")
                    .successHandler((request, response, authentication) -> {
                        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                        String redirectUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : "/main"; // 이전 URL이 있으면 그곳으로, 없으면 /main
                        response.sendRedirect(redirectUrl);
                    });
        });

        //로그인 토큰
        http.csrf(httpSecurityCsrfConfigurer -> {
            httpSecurityCsrfConfigurer.disable();
        });

        //리맴버 미 로그인 쿠키 저장
        http.rememberMe(httpSecurityRememberMeConfigurer -> {
            httpSecurityRememberMeConfigurer
                    .key("12345678")
                    .tokenRepository(persistentTokenRepository())
                    .userDetailsService(userDetailsService)
                    .tokenValiditySeconds(60*60*24*30);
        });

        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer
                    .accessDeniedHandler(accessDeniedHandler());
        });

        //소셜 로그인
        http.oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
            httpSecurityOAuth2LoginConfigurer
                    .loginPage("/login")
                    .successHandler(authenticationSuccessHandler());
        });

        //소셜 로그아웃
        http.logout(httpSecurityLogoutConfigurer -> {
            httpSecurityLogoutConfigurer
                    .logoutUrl("/logout");
        });

        //구글 로그인 테스트
//        http.authorizeHttpRequests(regi ->{
//            regi.requestMatchers("/").permitAll();
//            regi.anyRequest().authenticated();
//        })
//                .oauth2Login(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("------------web configure----------");
        return (web) -> web.ignoring().requestMatchers
                (PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

}
