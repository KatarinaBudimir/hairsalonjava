package hairsalon.hairsalon.controller;

import hairsalon.hairsalon.model.HairService;
import hairsalon.hairsalon.model.UserDetails;
import hairsalon.hairsalon.repositories.HairServiceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class HairServiceController {

    @Autowired
    private HairServiceRepository hairServiceRepository;


    @GetMapping("/hairsalon")
    public String getAllHairServices( Model model) {
        model.addAttribute("hairService", new HairService());
        model.addAttribute("hair_services", hairServiceRepository.findAll());

        return "hairsalon";
    }

    @GetMapping("/home")
    public String getCurrentUserReviews(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("hair_services", hairServiceRepository.findAll());
        return "home";
    }


    @PostMapping("/hair_services/add")
    public String addReview(@Valid HairService hairService, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hair_services", hairServiceRepository.findAll());
            return "hairsalon";
        }

        hairServiceRepository.save(hairService);
        return "redirect:/hairsalon";
    }
    @PostMapping("/hair_services/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        hairServiceRepository.deleteById(id);
        return "redirect:/hairsalon";
    }


    @PostMapping("/hair_services/edit/{id}")
    public String updateHairService(@PathVariable("id") Long id, @ModelAttribute("hairService") HairService hairService) {
        HairService existingHairService= hairServiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        existingHairService.setServiceName(hairService.getServiceName());
        existingHairService.setDescription(hairService.getDescription());
        existingHairService.setPrice(hairService.getPrice());

        hairServiceRepository.save(existingHairService);
        return "redirect:/hairsalon";
    }

}