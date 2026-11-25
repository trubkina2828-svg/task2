package ru.example.skladtehniki.controller;

import ru.example.skladtehniki.model.Equipment;
import ru.example.skladtehniki.model.Shipment;
import ru.example.skladtehniki.repository.EquipmentRepository;
import ru.example.skladtehniki.repository.ShipmentRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class ShipmentFormController {

    private final EquipmentRepository equipmentRepository;
    private final ShipmentRepository shipmentRepository;

    public ShipmentFormController(
            EquipmentRepository equipmentRepository,
            ShipmentRepository shipmentRepository) {
        this.equipmentRepository = equipmentRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @GetMapping("/shipments/new")
    public String showForm(Model model) {
        model.addAttribute(
                "equipmentList",
                equipmentRepository.findAll()
        );

        return "shipment-form";
    }

    @PostMapping("/shipments/save")
    public String saveShipment(
            @RequestParam Long equipmentId,
            @RequestParam Integer quantity,
            @RequestParam LocalDate shipmentDate,
            RedirectAttributes redirectAttributes) {

        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElse(null);

        if (equipment == null) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Техника не найдена"
            );
            return "redirect:/shipments/new";
        }

        if (quantity == null || quantity <= 0 ||
                quantity > equipment.getQuantity()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Недостаточно техники на складе"
            );
            return "redirect:/shipments/new";
        }

        if (shipmentDate == null || shipmentDate.isAfter(LocalDate.now())) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Проверьте дату отгрузки"
            );
            return "redirect:/shipments/new";
        }

        Shipment shipment = new Shipment();
        shipment.setEquipment(equipment);
        shipment.setQuantity(quantity);
        shipment.setShipmentDate(shipmentDate);

        shipmentRepository.save(shipment);

        equipment.setQuantity(equipment.getQuantity() - quantity);
        equipmentRepository.save(equipment);

        redirectAttributes.addFlashAttribute(
                "success",
                "Отгрузка успешно оформлена!"
        );

        return "redirect:/equipment";
    }
}
