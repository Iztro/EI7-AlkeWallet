package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import model.User;
import service.UserService;
import service.TwoFactorAuthenticationService;

@Controller
public class TwoFactorAuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private TwoFactorAuthenticationService twoFactorAuthenticationService;

    @GetMapping("/2fa-setup")
    public String setup2fa(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        String secret = twoFactorAuthenticationService.generateSecret();
        user.setSecret(secret);
        userService.save(user);
        String qrCodeUrl = "otpauth://totp/AlkeWallet:" + user.getUsername() + "?secret=" + secret;
        model.addAttribute("qrCodeUrl", qrCodeUrl);
        return "2fa-setup";
    }

    @PostMapping("/2fa-verify")
    public String verify2fa(String code, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        if (twoFactorAuthenticationService.verifyCode(user.getSecret(), code)) {
            user.setIs2faEnabled(true);
            userService.save(user);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid verification code");
            return "2fa-verify";
        }
    }

    @GetMapping("/2fa-verify")
    public String verify2faPage() {
        return "2fa-verify";
    }
}
