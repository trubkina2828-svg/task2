package ru.example.skladtehniki.service;

import ru.example.skladtehniki.model.Equipment;
import ru.example.skladtehniki.repository.EquipmentRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository repository;

    public EquipmentService(EquipmentRepository repository) {
        this.repository = repository;
    }

    public List<Equipment> findAll(
            String type,
            LocalDate arrivalDate) {

        Specification<Equipment> spec =
                (root, query, cb) -> cb.conjunction();

        if (type != null && !type.isBlank()) {
            String pattern = "%" + type.trim().toLowerCase() + "%";

            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("type")),
                            pattern
                    ));
        }

        if (arrivalDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(
                            root.get("arrivalDate"),
                            arrivalDate
                    ));
        }

        Sort sort = Sort.by(
                Sort.Order.asc("arrivalDate"),
                Sort.Order.asc("type").ignoreCase()
        );

        return repository.findAll(spec, sort);
    }

    public long countAll() {
        return repository.count();
    }

    public Equipment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Техника не найдена"
                        ));
    }

    public Equipment save(Equipment equipment) {
        return repository.save(equipment);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
