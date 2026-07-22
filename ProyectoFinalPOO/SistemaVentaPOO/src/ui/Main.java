package ui;

import dao.ClienteDAO;
import dao.ProductoDAO;
import dao.VentaDAO;
import model.Cliente;
import model.Producto;
import model.Venta;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        VentaDAO ventaDAO = new VentaDAO();

        // 🔹 Registrar cliente y obtener su ID
        Cliente c = new Cliente("Christopher Reyes", "chris@example.com");
        int idCliente = clienteDAO.registrarCliente(c);

        // Si el cliente ya existe, obtener su ID por correo
        if (idCliente == 0) {
            idCliente = clienteDAO.obtenerIdPorCorreo(c.getCorreo());
        }
        c.setId(idCliente);

        // 🔹 Registrar productos y obtener sus IDs
        Producto libro = new Producto(0, "El Principito", 25.50, "Libro");
        libro.setId(productoDAO.registrarProducto(libro));

        Producto cuaderno = new Producto(0, "Cuaderno Norma", 10.00, "Material Escolar");
        cuaderno.setId(productoDAO.registrarProducto(cuaderno));

        // 🔹 Registrar venta con el cliente correcto
        Venta v = new Venta(0, c);
        v.agregarProducto(libro);
        v.agregarProducto(cuaderno);
        ventaDAO.registrarVenta(v);

        // 🔹 Listar todas las ventas
        System.out.println("\n Listado general de ventas:");
        List<Venta> ventas = ventaDAO.listarVentas();
        for (Venta venta : ventas) {
            System.out.println(venta);
            venta.getProductos().forEach(p -> System.out.println("   - " + p));
        }

        // 🔹 Listar ventas por cliente
        System.out.println("\n Ventas del cliente con ID " + c.getId() + ":");
        List<Venta> ventasCliente = ventaDAO.listarVentasPorCliente(c.getId());
        for (Venta venta : ventasCliente) {
            System.out.println(venta);
            venta.getProductos().forEach(p -> System.out.println("   - " + p));
        }

        // 🔹 Mostrar total general
        double totalGeneral = ventaDAO.calcularTotalGeneral();
        System.out.println("\n Total general de todas las ventas: " + totalGeneral);

        // 🔹 Reportes avanzados para exposición
        ventaDAO.reporteVentasPorCliente();
        ventaDAO.reporteProductosMasVendidos();
        ventaDAO.reportePromedioVentas();
    }
}
