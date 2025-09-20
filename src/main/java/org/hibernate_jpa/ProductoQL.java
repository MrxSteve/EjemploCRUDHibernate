package org.hibernate_jpa;

import jakarta.persistence.EntityManager;
import org.hibernate_jpa.dominio.ProductoDto;
import org.hibernate_jpa.entity.Producto;
import org.hibernate_jpa.util.JpaUtil;

import java.util.List;

public class ProductoQL {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        System.out.println("========== consulta por campos personalizados ===========");
        Object[] objetoProducto = em.createQuery("select p.id, p.nombre, p.categoria from Producto p where p.id=:id", Object[].class)
                .setParameter("id", 1L)
                .getSingleResult();
        Long id = (Long) objetoProducto[0];
        String nombre = (String) objetoProducto[1];
        String categoria = (String) objetoProducto[2];
        System.out.println("id=" + id + ",nombre=" + nombre + ",categoria=" + categoria);

        System.out.println("========== consulta que puebla y devuelve objeto otro de una clase personalizada ==========");
        List<ProductoDto> productosDto = em.createQuery("select new org.hibernate_jpa.dominio.ProductoDto(p.nombre, p.categoria, p.precio) from Producto p", ProductoDto.class)
                .getResultList();
        productosDto.forEach(System.out::println);

        System.out.println("========== consulta para buscar por nombre ===========");
        String param = "LA";
        List<Producto> productos = em.createQuery("select p from Producto p where upper(p.nombre) like upper(:parametro)", Producto.class)
                .setParameter("parametro", "%" + param + "%")
                .getResultList();
        productos.forEach(System.out::println);

        System.out.println("========== consultas por rangos ==========");
        productos = em.createQuery("select p from Producto p where p.precio between 100.0 and 500.0", Producto.class).getResultList();
        productos.forEach(System.out::println);

        System.out.println("========== consulta para obtener el ultimo registro ==========");
        Producto ultimoProducto = em.createQuery("select p from Producto p where p.id = (select max(p.id) from Producto p)", Producto.class)
                .getSingleResult();
        System.out.println(ultimoProducto);

        em.close();
    }
}
