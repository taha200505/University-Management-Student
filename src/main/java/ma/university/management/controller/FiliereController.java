package ma.university.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.university.management.dto.FiliereDTO;
import ma.university.management.service.FiliereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/filieres")
@RequiredArgsConstructor
public class FiliereController {

    private final FiliereService filiereService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("filieres", filiereService.getAllFilieres());
        return "filiere/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("filiere", new FiliereDTO());
        model.addAttribute("isEdit", false);
        return "filiere/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("filiere") FiliereDTO dto,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "filiere/form";
        }
        filiereService.createFiliere(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Filière créée avec succès");
        return "redirect:/filieres";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("filiere", filiereService.getFiliereById(id));
        model.addAttribute("isEdit", true);
        return "filiere/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("filiere") FiliereDTO dto,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "filiere/form";
        }
        filiereService.updateFiliere(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Filière mise à jour avec succès");
        return "redirect:/filieres";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        filiereService.deleteFiliere(id);
        redirectAttributes.addFlashAttribute("successMessage", "Filière supprimée avec succès");
        return "redirect:/filieres";
    }
}
