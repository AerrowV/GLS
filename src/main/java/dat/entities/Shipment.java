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

}
