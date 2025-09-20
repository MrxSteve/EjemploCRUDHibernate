package org.hibernate_jpa.services;

import jakarta.persistence.EntityManager;
import org.hibernate_jpa.entity.Producto;
import org.hibernate_jpa.repositories.CrudRepository;
import org.hibernate_jpa.repositories.ProductoRepository;

import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService {
    private EntityManager em;
    private CrudRepository<Producto> repository;

    public ProductoServiceImpl(EntityManager em) {
        this.em = em;
        this.repository = new ProductoRepository(em);
    }

    @Override
    public List<Producto> listar() {
        return repository.listar();
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return Optional.ofNullable(repository.porId(id));
    }

    @Override
    public void guardar(Producto producto) {
        try {
            em.getTransaction().begin();
            repository.guardar(producto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            em.getTransaction().begin();
            repository.eliminar(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
