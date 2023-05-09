package com.inn.cafe.JWT;

import com.inn.cafe.Model.User;
import com.inn.cafe.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j

public class CustomerUsersDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;



  //  @Autowired
    private com.inn.cafe.Model.User userDetail;




    //@Bean
@Override
    public UserDetails loadUserByUsername(String email) throws
    //public User loadUserByUsername(String email) throws
            UsernameNotFoundException {

   User  userDetails =  userDao.findByEmailId(email);
   // User userDetails = userDao.findByName(email) ;
    log.info("userDetails from loadusername {}", userDetails.getName());
if (!Objects.isNull(userDetails)) {

    log.info("userDetails from if object {}", userDetails.getPassword());
    return new org.springframework.security.core.userdetails.User(userDetails.getName(), userDetails.getPassword(), new ArrayList<>());
}
   // return  userDetails;

else
    throw new UsernameNotFoundException("user not found from loaduserbyusernmae");
       // return null;
    }


   public com.inn.cafe.Model.User getUserDetail( String email){
   // public UserDetails getUserDetail(){
        userDetail =  userDao.findByEmailId(email);
   return userDetail;
    //return new org.springframework.security.core.userdetails.User();
    }

    @Bean

    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this::loadUserByUsername);
     //   authProvider.setUserDetailsService();
    authProvider.setPasswordEncoder(passwordEncoder());
    return  authProvider;
    }



    @Bean(name= BeanIds.AUTHENTICATION_MANAGER)

   public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
      // return super.authenticationManagerBean()
    }


    @Bean

    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }






}
