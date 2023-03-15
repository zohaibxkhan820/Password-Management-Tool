package com.password.tool.controller;


import com.password.tool.dto.UserDto;
import com.password.tool.exception.DuplicateUserException;
import com.password.tool.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String registerUser(@ModelAttribute("userBean") UserDto userDto, Map<String,String> model){
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("userBean") UserDto userDto, BindingResult result, Map<String,String> model ) throws DuplicateUserException {
        if(result.hasErrors()){
            return "register";
        }
        userService.saveUser(userDto);
        return "redirect:login";
    }

    @ExceptionHandler(value = DuplicateUserException.class)
    public String duplicateUserExceptionHandler(DuplicateUserException exception , Model model){
        model.addAttribute("errorMessage" , exception.getMessage());
        return "register";
    }

}
