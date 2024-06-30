package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import model.ConversionRequest;
import model.User;
import service.CurrencyService;
import service.UserService;

@Controller
public class CurrencyController {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/convert")
    public String showConversionForm(Model model) {
        model.addAttribute("currencies", currencyService.getExchangeRates().keySet());
        model.addAttribute("conversionRequest", new ConversionRequest());
        return "convert";
    }

    @PostMapping("/convert")
    public String convertCurrency(@ModelAttribute ConversionRequest request, Model model) {
        User user = userService.findByUsername(request.getUsername());
        if (user == null || request.getAmount() > user.getBalance()) {
            model.addAttribute("error", "Insufficient balance or user not found");
            return "convert";
        }
        double convertedAmount = currencyService.convert(request.getFromCurrency(), request.getToCurrency(), request.getAmount());
        model.addAttribute("result", convertedAmount);
        model.addAttribute("fromCurrency", request.getFromCurrency());
        model.addAttribute("toCurrency", request.getToCurrency());
        model.addAttribute("amount", request.getAmount());
        return "convert";
    }
}
