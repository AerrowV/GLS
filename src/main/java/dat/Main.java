package dat;

import dat.config.HibernateConfig;
import dat.dao.CustomPackageDAO;
import dat.entities.CustomPackage;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final CustomPackageDAO CUSTOM_PACKAGE_DAO = CustomPackageDAO.getInstance(emf);

    public static void main(String[] args) {

        // createPackages();
        // updatePackageStatus();
        // deletePackage();


//        String searchTrackingNumber = "1276746138754";
//        CustomPackage foundPackage = CUSTOM_PACKAGE_DAO.getPackageByTrackingNumber(searchTrackingNumber);
//
//        if (foundPackage != null) {
//            System.out.println("Found Package: " + foundPackage);
//        } else {
//            System.out.println("No package found with tracking number: " + searchTrackingNumber);
//        }
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