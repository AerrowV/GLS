package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private CustomPackage customPackage;

    @ManyToOne
    private Location sourceLocation;

    @ManyToOne
    private Location destinationLocation;

    private LocalDate localDate;

    private void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Shipment(CustomPackage customPackage, Location sourceLocation, Location destinationLocation, LocalDate localDate) {
        this.customPackage = customPackage;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.localDate = localDate;
    }
}
