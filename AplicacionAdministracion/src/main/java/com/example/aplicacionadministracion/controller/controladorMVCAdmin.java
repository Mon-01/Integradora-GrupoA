package com.example.aplicacionadministracion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controladorMVCAdmin {
    @GetMapping("/paso1")
    public String p1(){
        return "vista1";
    }

}
