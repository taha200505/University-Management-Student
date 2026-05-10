package ma.university.management.controller;

import lombok.RequiredArgsConstructor;
import ma.university.management.dto.DashboardDTO;
import ma.university.management.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        DashboardDTO stats = dashboardService.getDashboardStatistics();
        model.addAttribute("stats", stats);
        return "dashboard";
    }
}
