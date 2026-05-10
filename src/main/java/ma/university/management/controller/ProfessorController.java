package ma.university.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.university.management.dto.ProfessorDTO;
import ma.university.management.service.FiliereService;
import ma.university.management.service.ProfessorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;
    private final FiliereService filiereService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("professors", professorService.getAllProfessors());
        return "professor/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("professor", new ProfessorDTO());
        model.addAttribute("filieres", filiereService.getAllFilieres());
        model.addAttribute("isEdit", false);
        return "professor/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("professor") ProfessorDTO dto,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("filieres", filiereService.getAllFilieres());
            model.addAttribute("isEdit", false);
            return "professor/form";
        }
        professorService.createProfessor(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Professeur créé avec succès");
        return "redirect:/professors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("professor", professorService.getProfessorById(id));
        model.addAttribute("filieres", filiereService.getAllFilieres());
        model.addAttribute("isEdit", true);
        return "professor/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("professor") ProfessorDTO dto,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("filieres", filiereService.getAllFilieres());
            model.addAttribute("isEdit", true);
            return "professor/form";
        }
        professorService.updateProfessor(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Professeur mis à jour avec succès");
        return "redirect:/professors";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        professorService.deleteProfessor(id);
        redirectAttributes.addFlashAttribute("successMessage", "Professeur supprimé avec succès");
        return "redirect:/professors";
    }
}
