package dat.dao;

import dat.entities.Shipment;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class ShipmentDAO implements IDAO<Shipment, Long> {

    private static EntityManagerFactory emf;
    private static ShipmentDAO instance = null;

    private ShipmentDAO() {}

    public static ShipmentDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ShipmentDAO();
        }
        return instance;
    }

    @Override
    public Shipment create(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(shipment);
                em.getTransaction().commit();
                return shipment;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error creating shipment: " + e.getMessage());
            }
        }
    }

    @Override
    public Shipment findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Shipment.class, id);
        }
    }

    @Override
    public List<Shipment> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Shipment s", Shipment.class)
                    .getResultList();
        }
    }

    @Override
    public Shipment update(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                Shipment updatedShipment = em.merge(shipment);
                em.getTransaction().commit();
                return updatedShipment;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error updating shipment: " + e.getMessage());
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                Shipment shipment = em.find(Shipment.class, id);
                if (shipment != null) {
                    em.remove(shipment);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error deleting shipment: " + e.getMessage());
            }
        }
    }
}
