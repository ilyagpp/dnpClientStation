package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.service.CardService;
import com.example.dnpclientstation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User aUser,
            BindingResult bindingResult,
            Client client,
            Model model) {

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty){
            model.addAttribute("password2Error", "Поле не может быть пустым");
        }

        if (aUser.getPassword() != null && !aUser.getPassword().equals(passwordConfirm)){
            model.addAttribute("passwordError", "Пароли не совпадают");
        }

        if(isConfirmEmpty || bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "registration";
        }

        switch (userService.addUser(aUser)){
            case -1:
                model.addAttribute("usernameError", "Пользователь уже существует!");
                return "registration";

            case -2:
                model.addAttribute("emailError", "Пользователь c данным email уже существует!");
                return "registration";

            case 0:
                return "redirect:/login";

            default: return "er";
        }

    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if(isActivated){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        }else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }

}
