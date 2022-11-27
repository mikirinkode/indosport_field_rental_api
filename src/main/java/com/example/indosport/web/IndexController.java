package com.example.indosport.web;

import com.example.indosport.model.SportField;
import com.example.indosport.service.SportFieldSvcImpl;
import com.example.indosport.model.User;
import com.example.indosport.service.SecurityService;
import com.example.indosport.service.UserService;
import com.example.indosport.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private SportFieldSvcImpl sportFieldServiceImpl;

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
//        model.addAttribute("allsportfields", sportFieldServiceImpl.getAll());
        return "welcome";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/addnew")
    public String addNewSportField(Model model) {
        SportField sportField = new SportField();
        model.addAttribute("sportField", sportField);
        return "newfield";
    }

    @PostMapping("/save")
    public String saveFieldSport(@ModelAttribute("sportField") SportField sportField) {
        sportFieldServiceImpl.save(sportField);
        return "redirect:/";
    }

    @GetMapping("/updateSportField/{id}")
    public String updateForm(@PathVariable(value = "id") long id, Model model) {
        SportField sportField = sportFieldServiceImpl.getById(id);
        model.addAttribute("sportField", sportField);
        return "update";
    }

    @GetMapping("/deleteSportField/{id}")
    public String deleteThroughId(@PathVariable(value = "id") long id) {
        sportFieldServiceImpl.deleteViaId(id);
        return "redirect:/";
    }

    @GetMapping({"/search"})
    public String search(Model model, String keyword){
        System.out.println(keyword);
        if (keyword != null){
            List<SportField> list = sportFieldServiceImpl.search(keyword);
            model.addAttribute("allsportfields", list);
        } else {
            model.addAttribute("allsportfields", sportFieldServiceImpl.getAll());
        }
        return "welcome";
    }
}
