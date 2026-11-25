
package ru.example.skladtehniki.service;

import ru.example.skladtehniki.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public List<Map<String, Object>> getLast30DaysStatistics() {

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(29);

        List<Object[]> statistics =
                shipmentRepository.getShipmentStatistics(startDate);

        Map<LocalDate, Long> shipmentMap = new HashMap<>();

        for (Object[] row : statistics) {
            LocalDate date = (LocalDate) row[0];
            Long quantity = ((Number) row[1]).longValue();

            shipmentMap.put(date, quantity);
        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < 30; i++) {

            LocalDate date = startDate.plusDays(i);

            Map<String, Object> item = new HashMap<>();

            item.put("date", date.toString());
            item.put("quantity", shipmentMap.getOrDefault(date, 0L));

            result.add(item);
        }

        return result;
    }
}
