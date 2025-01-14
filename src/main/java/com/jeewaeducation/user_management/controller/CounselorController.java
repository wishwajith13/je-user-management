package com.jeewaeducation.user_management.controller;

import com.jeewaeducation.user_management.service.CounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/counselor")
@CrossOrigin
public class CounselorController {

    @Autowired
    private CounselorService counselorService;


}
