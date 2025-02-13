package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "location")
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double Latitude;
    private Double Longitude;
    private String Address;
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private Set<Shipment> sourceShipment = new HashSet<>();
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private Set<Shipment> destinationShipment = new HashSet<>();
}
