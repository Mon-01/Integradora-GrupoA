package com.example.aplicacioncorporativa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controladorMVCCorporativo {
    @GetMapping("/paso2")
    public String p2(){
        return "vista2";
    }


}
