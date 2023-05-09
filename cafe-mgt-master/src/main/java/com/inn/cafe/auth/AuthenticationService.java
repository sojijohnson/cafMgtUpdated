package com.inn.cafe.auth;

import com.inn.cafe.JWT.JwtUtil;
import com.inn.cafe.Model.User;
import com.inn.cafe.dao.UserDao;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//@NoArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
log.info("u got here");
var user= User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .role(request.getRole())
        .contactNumber(request.getContactNumber())
        .password(request.getPassword())
        .status(request.getStatus())
        .build();
        userDao.save(user);
        log.info("u got here 2 {}" ,user.getName());

        String jwtToken = jwtUtil.generateToken( user.getEmail(), user.getRole());
        log.info("u got here 3");
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        log.info("inside authethicate");

        try {

            Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPassword(),request.getEmail()));
            log.info("this the auth:{}",auth);
            if(auth.isAuthenticated()) {
                var user = userDao.findByEmailId(request.getEmail());
                log.info("this the user:{}", user);
                var jwtToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
                log.info("jwtoken {}", jwtToken);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }else {
                log.info("wait for approval");
            }

        }
        catch (Exception ex){


            log.error("{}",ex);
        }

       return AuthenticationResponse.builder().token("error").build();
}


}

// try {
//         Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
//         log.info("got here {}", customerUsersDetailsService.getUserDetail(requestMap.get("email")).getStatus());
//         log.info("inside login2");
//         log.info("auth value is {}", auth);
//         //   new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"));
//         if (auth.isAuthenticated()) {
//         if (customerUsersDetailsService.getUserDetail(requestMap.get("email")).getStatus().equalsIgnoreCase("true")) {
//
//         log.info("mail value is true");
//         return new ResponseEntity<String>("{\"token\": \"" +
//
//        jwtUtil.generateToken(customerUsersDetailsService.getUserDetail(requestMap.get("email")).getEmail(),
//        customerUsersDetailsService.getUserDetail(requestMap.get("email")).getRole()) + "\"}",
//        HttpStatus.OK);
//
//        } else {
//        return new ResponseEntity<String>("{\"message\": \"" + "wait for approal." + " \"}", HttpStatus.BAD_REQUEST);
//        }
//        }
//
//        }catch (Exception ex){
//        log.error("{}",ex);
//        }