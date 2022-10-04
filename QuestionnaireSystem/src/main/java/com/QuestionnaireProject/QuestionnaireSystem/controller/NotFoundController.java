package com.QuestionnaireProject.QuestionnaireSystem.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.QuestionnaireProject.QuestionnaireSystem.constant.UrlConstant;

@Controller
public class NotFoundController implements ErrorController {

    @RequestMapping(UrlConstant.Path.NOT_FOUND)
    public String handleNotFound() {
        return UrlConstant.Path.NOT_FOUND;
    }
    
}
