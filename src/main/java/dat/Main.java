package dat;

import dat.config.HibernateConfig;
import dat.dao.CustomPackageDAO;
import dat.dao.LocationDAO;
import dat.dao.ShipmentDAO;
import dat.entities.CustomPackage;
import dat.entities.Location;
import dat.entities.Shipment;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final CustomPackageDAO CUSTOM_PACKAGE_DAO = CustomPackageDAO.getInstance(emf);
    private static final LocationDAO LOCATION_DAO = LocationDAO.getInstance(emf);
    private static final ShipmentDAO SHIPMENT_DAO = ShipmentDAO.getInstance(emf);

    public static void main(String[] args) {

        createPackages();
        // updatePackageStatus();
        // deletePackage();


        String searchTrackingNumber = "1276746138754";
        CustomPackage foundPackage = CUSTOM_PACKAGE_DAO.getPackageByTrackingNumber(searchTrackingNumber);


        Location sourceLocation = new Location(55.6761, 12.5683, "Copenhagen, Denmark");
        sourceLocation = LOCATION_DAO.create(sourceLocation);

        Location destinationLocation = new Location(56.1629, 10.2039, "Aarhus, Denmark");
        destinationLocation = LOCATION_DAO.create(destinationLocation);;

        Shipment shipment = new Shipment(foundPackage, sourceLocation, destinationLocation, LocalDate.now());
        shipment = SHIPMENT_DAO.create(shipment);

    }

    private static void createPackages() {
        CustomPackage customPackage = CustomPackage.builder()
                .trackingNumber("127674612223")
                .senderName("Læge")
                .receiverName("Henrik Julie Hansen")
                .deliveryStatus(DeliveryStatus.PENDING)
                .build();
        customPackage = CUSTOM_PACKAGE_DAO.createPackage(customPackage);
        System.out.println("1. Created Package: \n" + customPackage);

        customPackage = CustomPackage.builder()
                .trackingNumber("1276746138754")
                .senderName("Bøde")
                .receiverName("Alti alti manden")
                .deliveryStatus(DeliveryStatus.IN_TRANSIT)
                .build();
        customPackage = CUSTOM_PACKAGE_DAO.createPackage(customPackage);
        System.out.println("1. Created Another Package: \n" + customPackage);
    }

    private static void updatePackageStatus() {
        String trackingNumber = "1276746138754";
        DeliveryStatus newStatus = DeliveryStatus.DELIVERED;

        CustomPackage updatedPackage = CUSTOM_PACKAGE_DAO.updatePackageStatus(trackingNumber, newStatus);

        System.out.println("Updated Package Status: \n" + updatedPackage);
    }

    private static void deletePackage() {
        String trackingNumber = "1276746138754";
        CUSTOM_PACKAGE_DAO.deletePackage(trackingNumber);
    }


}