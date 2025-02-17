package dat.dao;

import dat.entities.Location;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class LocationDAO implements IDAO<Location, Long> {

    private static EntityManagerFactory emf;
    private static LocationDAO instance = null;

    private LocationDAO() {}

    public static LocationDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new LocationDAO();
        }
        return instance;
    }

    @Override
    public Location create(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(location);
                em.getTransaction().commit();
                return location;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error creating location: " + e.getMessage());
            }
        }
    }

    @Override
    public Location findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Location.class, id);
        }
    }

    @Override
    public List<Location> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT l FROM Location l", Location.class)
                    .getResultList();
        }
    }

    @Override
    public Location update(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                Location updatedLocation = em.merge(location);
                em.getTransaction().commit();
                return updatedLocation;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error updating location: " + e.getMessage());
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                Location location = em.find(Location.class, id);
                if (location != null) {
                    em.remove(location);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(500, "Error deleting location: " + e.getMessage());
            }
        }
    }
}
