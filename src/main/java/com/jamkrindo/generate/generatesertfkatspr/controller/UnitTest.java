package com.jamkrindo.generate.generatesertfkatspr.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class UnitTest {


    @PostMapping("test")
    public @ResponseBody String callName(){
        return "Unit Test";
    }
}
