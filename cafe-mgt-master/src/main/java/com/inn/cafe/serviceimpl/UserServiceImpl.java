package com.inn.cafe.serviceimpl;

import com.inn.cafe.JWT.CustomerUsersDetailsService;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.JWT.JwtUtil;
import com.inn.cafe.Model.User;
import com.inn.cafe.constant.CafeConstants;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.EmailWrapper;
import com.inn.cafe.wrapper.UserWrapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@NoArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
@Autowired
private UserDao userDao;
@Autowired
private AuthenticationManager authenticationManager;


@Autowired
private JwtUtil jwtUtil;
    @Autowired
    private EmailWrapper emailWrapper;
@Autowired
private JwtFilter jwtFilter;
@Autowired
private CustomerUsersDetailsService customerUsersDetailsService;


    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {

        try{
      User user=  userDao.findByEmail(requestMap.get("email")) ;
           if(!Objects.isNull(user)){
              // if(user.getPassword().equalsIgnoreCase(requestMap.get()))
                 emailWrapper.forgotPasswordMail(user.getEmail(),"Password Reset",user.getPassword());
                 return CafeUtils.getResponseEntity("Kindly check your mail",HttpStatus.OK);
           }else{

               return CafeUtils.getResponseEntity("invalid eamil",HttpStatus.BAD_REQUEST);
           }
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return null;
    }

    @Override
    public ResponseEntity<String> signUp(User requestMap) {
        //return null;

        log.info("inside signup {}",requestMap);
      //  if (validateSignUpandMap(requestMap)){
User user = userDao.findByEmailId(requestMap.getEmail());
if(Objects.isNull(user)){
userDao.save((requestMap));
}
//else{
//    return CafeUtils.getResponseEntity("Email already",HttpStatus.BAD_REQUEST);
//}
      //  }
        else{

            return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("inside login");
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
            log.info("got here {}", customerUsersDetailsService.getUserDetail(requestMap.get("email")).getStatus());
            log.info("inside login2");
            log.info("auth value is {}", auth);
            //   new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"));
            if (auth.isAuthenticated()) {
                if (customerUsersDetailsService.getUserDetail(requestMap.get("email")).getStatus().equalsIgnoreCase("true")) {

                    log.info("mail value is true");
                    return new ResponseEntity<String>("{\"token\": \"" +

                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail(requestMap.get("email")).getEmail(),
                                    customerUsersDetailsService.getUserDetail(requestMap.get("email")).getRole()) + "\"}",
                            HttpStatus.OK);

                } else {
                    return new ResponseEntity<String>("{\"message\": \"" + "wait for approal." + " \"}", HttpStatus.BAD_REQUEST);
                }
            }

        }catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\": \"" + "Not approve." + " \"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {

        log.info("getall");

      try{
          if (jwtFilter.isAdmin()){

              return new ResponseEntity<>( userDao.getAllUser(),HttpStatus.OK);

          }else{
              return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
          }

      }catch (Exception ex){
           ex.printStackTrace();
        }


        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {

        try{
            if(jwtFilter.isAdmin()) {
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
              log.info("optional {}",optional.get().getEmail());


                if (!optional.isEmpty()) {

              Integer user  =  userDao.update(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));

                       log.info("successfull request email {}", requestMap.get("email"));
                    log.info("before email");
                    sendMailToAllAdmin(requestMap.get("status"),optional.get().getEmail(), userDao.getAllAdmin());
                    return  CafeUtils.getResponseEntity("update successful",HttpStatus.OK);
                  //  return new ResponseEntity<String>("{\"message\": \"" + "update approve." + " \"}", HttpStatus.OK);

                } else {
                    return CafeUtils.getResponseEntity("user not found", HttpStatus.BAD_REQUEST);
                }
            }else {
                return CafeUtils.getResponseEntity("unAuthorized entry", HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){

            ex.printStackTrace();
        }


        return CafeUtils.getResponseEntity("error, cannot update", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> resetPassword(Map<String, String> requestMap) {
 //input oldpaasword , newpassword and email
        try{
           // User user = userDao.findByEmail(requestMap.get("email"));
            User user = userDao.findByEmail(jwtFilter.getCurrentUser());
            if(!Objects.isNull(user)&& user.getPassword().equalsIgnoreCase(requestMap.get("oldpassword"))){
                user.setPassword(requestMap.get("newpassword"));
                log.info("newpassword {}",user.getPassword());
                userDao.save(user);
                return CafeUtils.getResponseEntity("password changed",HttpStatus.OK);
            }else{
                return CafeUtils.getResponseEntity("invalid email or oldpassword",HttpStatus.BAD_REQUEST);
            }

        }catch (Exception ex){

            ex.printStackTrace();
        }


        return CafeUtils.getResponseEntity("error, cannot update", HttpStatus.BAD_REQUEST);
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
     // User user1= (User) customerUsersDetailsService.loadUserByUsername(user);
      //log.info("user1 {}",user1);
        allAdmin.remove(jwtFilter.getCurrentUser());
        if( status !=null && status.equalsIgnoreCase("true")){
            emailWrapper.sendSimpleMessage( jwtFilter.getCurrentUser(), "Account Approved","User:-"+ user+"\n is approved by\nAdmin:-"+jwtFilter.getCurrentUser(),allAdmin);
        }else{
            emailWrapper.sendSimpleMessage( jwtFilter.getCurrentUser(), "Account disabled","User:-"+ user+"\n is disabled by\nAdmin:-"+jwtFilter.getCurrentUser(),allAdmin);

        }

    }

//    private boolean validateSignUpandMap(User  requestMap){
//
//      if( requestMap.getName() && requestMap.getContactNumber()
//               && requestMap.getEmail()&& requestMap.getPassword()){
//  return  false;
//    }
//      return  true;
//    }

    private User getUserFromMap(User requestMap){
        User user = new User();
        user.setEmail(requestMap.getEmail());
        user.setRole("user");
        user.setPassword(requestMap.getPassword());
        user.setContactNumber(requestMap.getContactNumber());
         user.setStatus("false");
         user.setName(requestMap.getName());

        return user;
    }

//    public ResponseEntity<String> signUp(User requestMap) {
//        return null;
//    }
}
