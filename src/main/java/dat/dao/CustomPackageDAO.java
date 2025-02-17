package dat.dao;
import dat.DeliveryStatus;
import dat.entities.CustomPackage;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

public class CustomPackageDAO {

    private static EntityManagerFactory emf;
    private static CustomPackageDAO instance = null;

    private CustomPackageDAO() {}

    public static CustomPackageDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new CustomPackageDAO();
        }
        return instance;
    }

    public CustomPackage createPackage(CustomPackage _Custom_package) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(_Custom_package);
                em.getTransaction().commit();
                return _Custom_package;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error creating shipment");
            }
        }
    }
    public CustomPackage updatePackageStatus(String trackingNumber, DeliveryStatus newStatus) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                CustomPackage existingPackage = em.createQuery(
                                "SELECT p FROM CustomPackage p WHERE p.trackingNumber = :trackingNumber", CustomPackage.class)
                        .setParameter("trackingNumber", trackingNumber)
                        .getSingleResult();

                existingPackage.setDeliveryStatus(newStatus);
                CustomPackage updatedPackage = em.merge(existingPackage);
                em.getTransaction().commit();
                return updatedPackage;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error updating package status: " + e.getMessage());
            }
        }
    }


    public void deletePackage(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                CustomPackage deletePackage = em.createQuery(
                                "SELECT p FROM CustomPackage p WHERE p.trackingNumber = :trackingNumber", CustomPackage.class)
                        .setParameter("trackingNumber", trackingNumber)
                        .getSingleResult();

                em.remove(deletePackage);
                em.getTransaction().commit();
                System.out.println("Package with tracking number " + trackingNumber + " deleted successfully.");
            } catch (NoResultException e) {
                em.getTransaction().rollback();
                System.out.println("No package found with tracking number: " + trackingNumber);
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error updating package status: " + e.getMessage());
            }
        }
    }

    public CustomPackage getPackageByTrackingNumber(String trackingNumber) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM CustomPackage p WHERE p.trackingNumber = :trackingNumber", CustomPackage.class)
                    .setParameter("trackingNumber", trackingNumber)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(500, "Error getting package");
        }
    }
}
