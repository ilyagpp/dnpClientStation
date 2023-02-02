package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.AZS;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.service.AZSService;
import com.example.dnpclientstation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AZSController {

    @Autowired
    AZSService azsService;

    @Autowired
    UserService userService;


    @GetMapping("/azs")
    public String azsList(Model model) {

        List<AZS> azsList = new ArrayList<>();
        azsList = azsService.getAll();

        model.addAttribute("azslist", azsList);

        return "azs";

    }

    @GetMapping("/azs/new")
    public String newAzs(Model model,
                         @RequestParam(required = false) AZS azs) {

        getFreeUsers(model);

        return "azsNew";
    }


    @PostMapping("/azs/profile")
    public String azsProfilePage(Model model,
                                 @Valid AZS azs,
                                 BindingResult bindingResult,
                                 @RequestParam(required = false) String edit,
                                 @RequestParam(required = false) Map<String, String> form,
                                 @RequestHeader(required = false) String referer) {

        Set<String> azsIds = new TreeSet<>();

        getFreeUsers(model);

        if (edit != null) model.addAttribute("edit", true);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "azsNew";
        }

        for (Map.Entry<String, String> map : form.entrySet()) {
            if (map.getValue().equals("on")) {
                azsIds.add(map.getKey());
            }
        }

        azsService.createOrUpdate(azs, azsIds);

        return "redirect:/azs";

    }

    @GetMapping("/azs/{azsId}")
    public String getAzs(@PathVariable Long azsId,
                         Model model) {

        AZS azs = azsService.findById(azsId);
        if (!azs.isNew()) {
            model.addAttribute("edit", true);
            model.addAttribute("azs", azs);
        }
        getFreeUsers(model);


        return "azsNew";

    }


    private void getFreeUsers(Model model) {
        List<User> users = userService.findAllOrderById().stream()
                .filter(user -> user.getAzs() == null)
                .collect(Collectors.toList());
        model.addAttribute("freeUsers", users);
    }


}
