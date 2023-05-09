package com.inn.cafe.JWT;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurationAdapter;



@EnableWebSecurity
@Configuration
//@RequiredArgsConstructor
public class SecurityConfig   {
@Autowired
    CustomerUsersDetailsService customerUsersDetailsService;
@Autowired
private JwtFilter jwtFilter;
@Autowired

private AuthenticationProvider authenticationProvider;


 //from amingos

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
    http
          //  .cors()
          //  .and()
           .csrf()
           .disable()
            .authorizeHttpRequests()
            .requestMatchers("/category/*","/user/login","/user/*","/user/getall","/api/auth/register","/api/auth/authenticate","/user/signup","/user/forgotPassword")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();


}







   // @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(customerUsersDetailsService);
//        //        UserDetails user = User.withDefaultPasswordEncoder()
////                .username("user")
////                .password("password")
////                .roles("USER")
////                .build();
////        auth.jdbcAuthentication()
////                .withDefaultSchema()
////                .dataSource(dataSource())
////                .withUser(user);
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
//    @Bean(name= BeanIds.AUTHENTICATION_MANAGER)
//
//    AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//       // configure(http);
//         http.cors().configurationSource(request->new CorsConfiguration().applyPermitDefaultValues())
//                 .and()
//                 .csrf()
//                 .disable()
//                 .authorizeHttpRequests()
//                 .requestMatchers("/user/login","/api/auth/register","/user/signup","/user/forgotPassword")
//                 .permitAll()
//                 .anyRequest()
//                 .authenticated()
//                 .and().exceptionHandling()
//                 .and()
//                 .sessionManagement()
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        // adding the custom filter before UsernamePasswordAuthenticationFilter in the filter chain
//       // http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//  //
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}
