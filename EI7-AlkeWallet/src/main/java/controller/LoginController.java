package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login2")
public class LoginController {

    @GetMapping
    public String login2(Model model) {
        return "login2";
    }
}
