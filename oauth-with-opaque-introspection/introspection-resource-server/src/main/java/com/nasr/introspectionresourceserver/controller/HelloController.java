package com.nasr.introspectionresourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello man";

        //we can set userDetail service for this project and authentication like form login
        //beside of e most use this api in another service then addition of userDetailService for using of this service
        //we need to specify resource server for another service to access to this service using of oauth
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
