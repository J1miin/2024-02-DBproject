package com.example.bbungeobbang.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
    @GetMapping("/")
    public String mainP(Model model) {
        model.addAttribute("appName", "BungEoBbang");
        model.addAttribute("serverTime", System.currentTimeMillis());
        return "index";
    }
}
