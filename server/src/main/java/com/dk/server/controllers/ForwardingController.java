package com.dk.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardingController {
    @GetMapping("/**/{path:[^\\\\.]*}")
    public String forward() {
        return "forward:/";
    }
}