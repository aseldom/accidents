package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.SimpleDataAccidentService;

@Controller
@AllArgsConstructor
public class IndexController {

    private SimpleDataAccidentService simpleAccidentDataService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", simpleAccidentDataService.findAll());
        return "index";
    }
}
