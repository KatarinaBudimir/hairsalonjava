package hairsalon.hairsalon.controller;

import hairsalon.hairsalon.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import hairsalon.hairsalon.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepo;

    private static final String ADMIN_PASSWORD = "diva123";

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String register (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register_user")
    public String register_user (@Valid User user, BindingResult result, @RequestParam String adminPassword, Model model) {
        if (!ADMIN_PASSWORD.equals(adminPassword)) {
            model.addAttribute("user", user);
            model.addAttribute("success", false);
            model.addAttribute("errorMessage", "Admin lozinka je pogrešna.");
            return "register";
        }
        if (userRepo.findByEmail(user.getEmail()) != null) {
            result.addError(new FieldError("user", "email", "Korisnik je već registriran s ovom email adresom, molimo pokušajte koristiti drugu."));
        }
        if (!user.getPassword().equals(user.getPasswordRepeat())) {
            result.addError(new FieldError("user", "passwordRepeat", "Lozinke se moraju podudarati"));
            result.addError(new FieldError("user", "password", "Lozinke se moraju podudarati"));
        }

        boolean errors = result.hasErrors();
        if (errors) {
            model.addAttribute("user", user); // podaci koji su uredu šalju se formi na prikaz
            model.addAttribute("success", false); // sakrij poruku da je sve ok
            return "register";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);

        model.addAttribute("user", new User());
        model.addAttribute("success", true);
        return "register";
    }

    @GetMapping("/**")
    public String handleAllUndefinedRoutes() {
        return "redirect:/home";
    }
}