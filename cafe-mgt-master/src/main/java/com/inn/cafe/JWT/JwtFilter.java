package com.inn.cafe.JWT;

import com.inn.cafe.dao.UserDao;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
@Autowired
    private JwtUtil jwtUtil;

@Autowired
private UserDao userDao;
@Autowired
private CustomerUsersDetailsService customerUsersDetailsService;
Claims claims=null;
private String userName;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

if (request.getServletPath().matches("user/login|/api/auth/register|/api/auth/authenticate|/user/forgotPassword|/user/signup")){
    filterChain.doFilter(request,response);

}else{
    String authorizationHeader = request.getHeader("Authorization");
    String token =null;
   if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
     //  if(authorizationHeader ==null || authorizationHeader.startsWith("Bearer ")){
                  token = authorizationHeader.substring(7);
                  userName = jwtUtil.extractUsername(token);
                  claims = jwtUtil.extractAllClaims(token);

    }
    if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
             log.info("hello deeedede {}", userName);
        log.info("hello claims {}", claims);

       // String username=  userDao.findByName(userName);


          // if(username.)
              //  log.info("from db username {}",username);

        UserDetails userDetails= customerUsersDetailsService.loadUserByUsername(userName);
        if(jwtUtil.validateToken(token,userDetails)){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

    }
            log.info("failded");
   filterChain.doFilter(request,response);
}
      // filterChain.doFilter(request,response);

    }
    public boolean isAdmin(){

        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser(){

        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){

        return userName;
    }
}
