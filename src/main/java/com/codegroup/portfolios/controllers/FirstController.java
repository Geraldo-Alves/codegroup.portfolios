package com.codegroup.portfolios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegroup.portfolios.dto.Response;
import com.codegroup.portfolios.services.ServerService;

@RestController
@RequestMapping("/first")
public class FirstController extends BaseController {

    @Autowired
    private ServerService serverService;

    @GetMapping
    public Response first() {
        return super.success(serverService.hello());
    }

    @GetMapping("/error")
    public Response firstError() {
        return super.success(serverService.error());
    }

}