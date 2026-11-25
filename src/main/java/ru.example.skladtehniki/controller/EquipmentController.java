package ru.example.skladtehniki.controller;

import ru.example.skladtehniki.model.Equipment;
import ru.example.skladtehniki.service.EquipmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String arrivalDate,
            Model model) {

        LocalDate date = null;

        if (arrivalDate != null && !arrivalDate.isBlank()) {
            date = LocalDate.parse(arrivalDate);
        }

        model.addAttribute(
                "equipmentList",
                equipmentService.findAll(type, date)
        );

        model.addAttribute(
                "totalCount",
                equipmentService.countAll()
        );

        return "equipment";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("equipment", new Equipment());
        return "equipment-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Equipment equipment) {
        equipmentService.save(equipment);
        return "redirect:/equipment";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "equipment",
                equipmentService.findById(id)
        );

        return "equipment-form";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return "redirect:/equipment";
    }
}
