package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.SimpleAccidentService;

@Controller
@AllArgsConstructor
public class IndexController {

    private SimpleAccidentService simpleAccidentService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", simpleAccidentService.findAll());
        return "index";
    }
}
