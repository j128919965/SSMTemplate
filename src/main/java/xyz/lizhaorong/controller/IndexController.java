package xyz.lizhaorong.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.lizhaorong.security.web.Authorization;
import xyz.lizhaorong.service.SbService;
import xyz.lizhaorong.util.support.Response;

@Controller
@RequestMapping
@Slf4j
public class IndexController {

    @Autowired
    private SbService sbService;


    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    @GetMapping("/sb")
    @ResponseBody
    public Response getSb(){
        return Response.success(sbService.geUserById(1));
    }


    @GetMapping
    @Authorization(1)
    public String index(){
        log.debug("index");
        return "index";
    }

}
