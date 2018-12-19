package com.sprinklr.socialapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialAppController {
	
	@GetMapping("/")
    public String handler() {
		return "Sprinklr Social App Is Running ..!!";
    }

}
