package ma.university.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.university.management.dto.CourseDTO;
import ma.university.management.service.CourseService;
import ma.university.management.service.FiliereService;
import ma.university.management.service.ProfessorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final FiliereService filiereService;
    private final ProfessorService professorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "course/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new CourseDTO());
        model.addAttribute("filieres", filiereService.getAllFilieres());
        model.addAttribute("professors", professorService.getAllProfessors());
        model.addAttribute("isEdit", false);
        return "course/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("course") CourseDTO dto,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("filieres", filiereService.getAllFilieres());
            model.addAttribute("professors", professorService.getAllProfessors());
            model.addAttribute("isEdit", false);
            return "course/form";
        }
        courseService.createCourse(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Module créé avec succès");
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("course", courseService.getCourseById(id));
        model.addAttribute("filieres", filiereService.getAllFilieres());
        model.addAttribute("professors", professorService.getAllProfessors());
        model.addAttribute("isEdit", true);
        return "course/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("course") CourseDTO dto,
                         BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("filieres", filiereService.getAllFilieres());
            model.addAttribute("professors", professorService.getAllProfessors());
            model.addAttribute("isEdit", true);
            return "course/form";
        }
        courseService.updateCourse(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Module mis à jour avec succès");
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("successMessage", "Module supprimé avec succès");
        return "redirect:/courses";
    }
}
