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

    private Double latitude;
    private Double longitude;
    private String address;

    @OneToMany(mappedBy = "sourceLocation", cascade = CascadeType.ALL)
    private Set<Shipment> sourceLocation = new HashSet<>();

    @OneToMany(mappedBy = "destinationLocation", cascade = CascadeType.ALL)
    private Set<Shipment> destinationLocation = new HashSet<>();

    public void addSourceLocation(Shipment shipment) {
        if (sourceLocation != null) {
            sourceLocation.add(shipment);
        }
    }

    public void addDestinationLocation(Shipment shipment) {
        if (destinationLocation != null) {
            destinationLocation.add(shipment);
        }
    }

    public Location(Double latitude, Double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}
