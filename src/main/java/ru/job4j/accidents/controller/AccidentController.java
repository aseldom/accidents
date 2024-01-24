package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.SimpleAccidentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

    private final SimpleAccidentService simpleAccidentService;

    /**
     * Страница создания инцидента
     */
    @GetMapping("createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = new ArrayList<>();
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        model.addAttribute("types", types);
        return "accidents/createAccident";
    }

    /**
     * Сохранение созданного инцидента
     */
    @PostMapping("saveAccident")
    public String save(@ModelAttribute Accident accident, Model model) {
        if (simpleAccidentService.add(accident) == null) {
            model.addAttribute("message", "Инцидент добавить не удалось");
            return "errors/404";
        }
        return "redirect:/index";
    }

    /**
     * Страница редактирования инцидента
     */
    @GetMapping("formUpdateAccident")
    public String update(@RequestParam int id, Model model) {
        Optional<Accident> optionalAccident = simpleAccidentService.findById(id);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("message", "Инцидент не найден");
            return "errors/404";
        }
        model.addAttribute("accident", optionalAccident.get());
        return "accidents/editAccident";
    }

    /**
     * Обновление отредактированного инцидента
     */
    @PostMapping("updateAccident")
    public String update(@ModelAttribute Accident accident, Model model) {
        if (!simpleAccidentService.update(accident)) {
            model.addAttribute("message", "Инцидент с указанным идентификатором изменить не удалось");
            return "errors/404";
        }
        return "redirect:/index";
    }
}