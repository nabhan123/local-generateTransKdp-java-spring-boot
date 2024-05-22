package com.jamkrindo.generate.generatesertfkatspr.controller.testing;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableScheduling
@RestController
@RequestMapping("api/sch")
public class Schedulling {


//    @Scheduled(fixedRate = 5000) // setiap 5 detik sekali
    @PostMapping("task")
    public void doTask(){
        System.out.println("schedule task start");
    }


}
