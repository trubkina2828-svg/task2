package ru.example.skladtehniki.repository;

import ru.example.skladtehniki.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    @Query("""
        SELECT s.shipmentDate, SUM(s.quantity)
        FROM Shipment s
        WHERE s.shipmentDate >= :startDate
        GROUP BY s.shipmentDate
        ORDER BY s.shipmentDate
    """)
    List<Object[]> getShipmentStatistics(
            @Param("startDate") LocalDate startDate
    );
}
