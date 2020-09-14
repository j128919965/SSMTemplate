package xyz.lizhaorong.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.lizhaorong.entity.User;
import xyz.lizhaorong.service.SBService;
import xyz.lizhaorong.web.util.Response;
import xyz.lizhaorong.security.authorization.Authorization;

import javax.validation.Valid;

@RequestMapping("/home")
@Controller
@Authorization
public class HomeController {

    @Autowired
    SBService service;

    @GetMapping("/{id}")
    public String helloTest(@PathVariable("id") int id){
        System.out.println(service.geUserById(id));
        return "success";
    }

    @GetMapping("/sb")
    @ResponseBody
    public Response getsb(){
        return Response.success(service.geUserById(1));
    }

    @PostMapping("/psb")
    @ResponseBody
    public Response postUser(@Valid User user){
        System.out.println(user);
        return Response.success();
    }

}