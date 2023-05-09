package com.inn.cafe.rest;

import com.inn.cafe.Model.User;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RequestMapping(path="/user")
public interface UserRest {


@PostMapping(path="/signup")
    public ResponseEntity<String>signUp(@RequestBody(required=true) User requestMap);

@GetMapping(path="/getall")
public  ResponseEntity<List<UserWrapper>>getAllUser();


    @PostMapping(path="/login")
    public ResponseEntity<String>login(@RequestBody(required=true) Map<String, String> requestMap);

    @PostMapping(path="/update")
    public ResponseEntity<String>update(@RequestBody(required = true) Map<String,String> requestMap);





    @PostMapping(path="/resetpassword")
    public ResponseEntity<String>resetPassword(@RequestBody(required = true) Map<String,String>requestMap);
    @PostMapping(path="/forgotpassword")
    public ResponseEntity<String>forgotPassword(@RequestBody(required = true) Map<String,String>requestMap);


}
