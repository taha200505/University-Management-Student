package ma.university.management.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.university.management.dto.StudentDTO;
import ma.university.management.entity.enums.Level;
import ma.university.management.entity.enums.StudentStatus;
import ma.university.management.service.CsvExportService;
import ma.university.management.service.FiliereService;
import ma.university.management.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;
    private final FiliereService filiereService;
    private final CsvExportService csvExportService;

    // ========== LIST + SEARCH + FILTER ==========

    @GetMapping
    public String listStudents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long filiereId,
            @RequestParam(required = false) Level level,
            @RequestParam(required = false) StudentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model) {

        Sort sortOrder = dir.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<StudentDTO> studentPage;
        if (keyword != null && !keyword.isBlank()) {
            studentPage = studentService.searchStudents(keyword.trim(), pageable);
        } else {
            studentPage = studentService.filterStudents(filiereId, level, status, pageable);
        }

        model.addAttribute("students", studentPage);
        model.addAttribute("filieres", filiereService.getAllFilieres());
        model.addAttribute("levels", Level.values());
        model.addAttribute("statuses", StudentStatus.values());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedFiliereId", filiereId);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentDir", dir);

        return "student/list";
    }

    // ========== VIEW ==========

    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "student/view";
    }

    // ========== CREATE ==========

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("filieres", filiereService.getAllFilieres());
        model.addAttribute("levels", Level.values());
        model.addAttribute("statuses", StudentStatus.values());
        model.addAttribute("isEdit", false);
        return "student/form";
    }

    @PostMapping("/new")
    public String createStudent(@Valid @ModelAttribute("student") StudentDTO dto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("filieres", filiereService.getAllFilieres());
            model.addAttribute("levels", Level.values());
            model.addAttribute("statuses", StudentStatus.values());
            model.addAttribute("isEdit", false);
            return "student/form";
        }

        studentService.createStudent(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Étudiant créé avec succès");
        return "redirect:/students";
    }

    // ========== EDIT ==========

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("filieres", filiereService.getAllFilieres());
        model.addAttribute("levels", Level.values());
        model.addAttribute("statuses", StudentStatus.values());
        model.addAttribute("isEdit", true);
        return "student/form";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") StudentDTO dto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("filieres", filiereService.getAllFilieres());
            model.addAttribute("levels", Level.values());
            model.addAttribute("statuses", StudentStatus.values());
            model.addAttribute("isEdit", true);
            return "student/form";
        }

        studentService.updateStudent(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Étudiant mis à jour avec succès");
        return "redirect:/students";
    }

    // ========== DELETE ==========

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Étudiant supprimé avec succès");
        return "redirect:/students";
    }

    // ========== CSV EXPORT ==========

    @GetMapping("/export/csv")
    public void exportToCsv(HttpServletResponse response) throws Exception {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=etudiants.csv");

        List<StudentDTO> students = studentService.getAllStudentsList();
        csvExportService.exportStudentsToCsv(students,
                new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
    }
}
