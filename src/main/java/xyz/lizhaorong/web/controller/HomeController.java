package xyz.lizhaorong.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.lizhaorong.entity.User;
import xyz.lizhaorong.service.SBService;
import xyz.lizhaorong.web.util.Response;
import xyz.lizhaorong.security.webUtil.Authorization;

import javax.validation.Valid;

@RequestMapping("/home")
@Controller
@Authorization(1)
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
    public Response getsb(int id){
        return Response.success(service.geUserById(id));
    }

    @PostMapping("/psb")
    @ResponseBody
    public Response postUser(@Valid User user){
        System.out.println(user);
        return Response.success();
    }

    @PostMapping("/psb2")
    @ResponseBody
    public Response postUserBody(@RequestBody User user){
        System.out.println(user);
        return Response.success();
    }

}