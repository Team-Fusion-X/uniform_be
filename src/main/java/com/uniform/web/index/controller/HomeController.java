package com.uniform.web.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("hello")
    public String hello(@org.jetbrains.annotations.NotNull Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello";
    }
}
