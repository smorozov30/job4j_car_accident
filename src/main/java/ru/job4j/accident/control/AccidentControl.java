package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.Service;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccidentControl {
    private final Service accidents;

    public AccidentControl(Service accidents) {
        this.accidents = accidents;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", accidents.getTypes());
        model.addAttribute("rules", accidents.getRules());
        return "accident/create";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidents.getAccidentById(id));
        model.addAttribute("types", accidents.getTypes());
        model.addAttribute("rules", accidents.getRules());
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidents.save(accident, ids);
        return "redirect:/";
    }
}
