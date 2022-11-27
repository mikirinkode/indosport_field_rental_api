package com.example.indosport.web;

import com.example.indosport.model.SportField;
import com.example.indosport.model.User;
import com.example.indosport.repository.UserRepository;
import com.example.indosport.service.SecurityService;
import com.example.indosport.service.SportFieldSvcImpl;
import com.example.indosport.service.UserService;
import com.example.indosport.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private SportFieldSvcImpl sportFieldServiceImpl;

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        User user = userService.getLoggedUser();
        model.addAttribute("allsportfields", sportFieldServiceImpl.getUserSportField(user.getId()));
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
    public String registration(@ModelAttribute("userForm") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(user);

        securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());

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
        User user = userService.getLoggedUser();
        sportField.setUser(user);

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
    public String search(Model model, String keyword) {
        User user = userService.getLoggedUser();

        if (keyword != null) {
            List<SportField> list = sportFieldServiceImpl.search(keyword, user.getId());
            if (list.isEmpty()) {
                model.addAttribute("message", "Data not found.");
            } else {
                model.addAttribute("allsportfields", list);
            }
        } else {
            model.addAttribute("allsportfields", sportFieldServiceImpl.getUserSportField(user.getId()));
        }
        return "welcome";
    }
}
