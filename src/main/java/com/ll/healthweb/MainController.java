package com.ll.healthweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String root() {
        return "redirect:/daily/list";
    }

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
