package org.hibernate_jpa.repositories;

import jakarta.persistence.EntityManager;
import org.hibernate_jpa.entity.Producto;

import java.util.List;

public class ProductoRepository implements CrudRepository<Producto> {
    private EntityManager em;

    public ProductoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Producto> listar() {
        return em.createQuery("select p from Producto p", Producto.class).getResultList();
    }

    @Override
    public Producto porId(Long id) {
        return em.find(Producto.class, id);
    }

    @Override
    public void guardar(Producto producto) {
        if(producto.getId() != null && producto.getId() > 0) {
            em.merge(producto);
        } else {
            em.persist(producto);
        }
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = porId(id);
        em.remove(producto);
    }
}
