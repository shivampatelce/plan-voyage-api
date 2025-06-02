package com.example.plan_voyage.controller;

import com.example.plan_voyage.util.BaseController;
import com.example.plan_voyage.util.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/playground")
public class PlaygroundController extends BaseController {

    @GetMapping
    public ResponseEntity<SuccessResponse<String>> playgorund() {
        return success("Playground test");
    }
}
