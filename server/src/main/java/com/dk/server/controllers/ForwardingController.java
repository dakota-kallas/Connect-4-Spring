package com.dk.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardingController {
    @RequestMapping("^(?!/assets/)/{path:[^\\.]+}/**")
    public String forward() {
        return "forward:/";
    }
}
