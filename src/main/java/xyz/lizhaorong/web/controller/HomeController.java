package xyz.lizhaorong.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.lizhaorong.service.SBService;
import xyz.lizhaorong.web.util.Response;
import xyz.lizhaorong.web.util.authorization.Authorization;

@RequestMapping("/home")
@Controller
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
    @Authorization
    public Response getsb(){
        return Response.success(service.geUserById(1));
    }

}