package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.*;

import java.util.Optional;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    /**
     * Добавление инцидента
     */
    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("user", getUser());
        return "accidents/createAccident";
    }

    /**
     * Редактирование инцидента
     */
    @GetMapping("/formUpdateAccident")
    public String update(@RequestParam int id, Model model) {
        Optional<Accident> optionalAccident = accidentService.findById(id);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("message", "Инцидент не найден");
            model.addAttribute("user", getUser());
            return "errors/404";
        }
        model.addAttribute("accident", optionalAccident.get());
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("user", getUser());
        return "accidents/editAccident";
    }

    /**
     * Сохранение/обновление инцидента
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam(required = false) Set<Integer> rIds, Model model) {
        if (!accidentService.update(accident, rIds)) {
            model.addAttribute("message", "Инцидент добавить/изменить не удалось");
            model.addAttribute("user", getUser());
            return "errors/404";
        }
        return "redirect:/index";
    }

    private Object getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}