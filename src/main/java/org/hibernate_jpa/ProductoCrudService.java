package org.hibernate_jpa;

import jakarta.persistence.EntityManager;
import org.hibernate_jpa.entity.Producto;
import org.hibernate_jpa.services.ProductoService;
import org.hibernate_jpa.services.ProductoServiceImpl;
import org.hibernate_jpa.util.JpaUtil;

import java.util.List;
import java.util.Optional;

public class ProductoCrudService {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        ProductoService service = new ProductoServiceImpl(em);

        System.out.println("========== listar ==========");
        List<Producto> productos = service.listar();
        productos.forEach(System.out::println);

        System.out.println("========== insertar productos de prueba ===========");
        Producto p1 = new Producto("Pizza", "comida", "pizza de muzzarella", 500.00);
        service.guardar(p1);
        Producto p2 = new Producto("Hamburguesa", "comida", "hamburguesa con papas fritas", 700.00);
        service.guardar(p2);
        Producto p3 = new Producto("Coca Cola", "bebida", "gaseosa de 500ml", 250.00);
        service.guardar(p3);
        Producto p4 = new Producto();
        p4.setNombre("Agua Mineral");
        p4.setCategoria("bebida");
        p4.setDescripcion("agua sin gas de 500ml");
        p4.setPrecio(150.00);
        service.guardar(p4);
        System.out.println("productos guardados con exito");
        service.listar().forEach(System.out::println);

        System.out.println("========== obtener por id ==========");
        Optional<Producto> optionalProducto = service.porId(1L);
        optionalProducto.ifPresent(System.out::println);

        System.out.println("=========== editar producto ==========");
        Long id = p1.getId();
        optionalProducto = service.porId(id);
        optionalProducto.ifPresent(p -> {
            p.setNombre("Pizza Grande de queso");
            service.guardar(p);
            System.out.println("producto editado con exito!");
            service.listar().forEach(System.out::println);
        });

        System.out.println("========== eliminar producto ===========");
        id = p2.getId();
        optionalProducto = service.porId(id);
        optionalProducto.ifPresent(p -> {
            service.eliminar(p.getId());
            System.out.println("producto eliminado con exito!");
            service.listar().forEach(System.out::println);
        });
        em.close();
    }
}
