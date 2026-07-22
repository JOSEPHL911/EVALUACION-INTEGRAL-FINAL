package dao;

import model.Venta;
import model.Producto;
import model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    // 🔹 Registrar una nueva venta
    public void registrarVenta(Venta v) {
        if (v.getProductos().isEmpty() || v.getTotal() <= 0) {
            System.out.println("No se puede registrar una venta sin productos o con total 0.");
            return;
        }

        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sqlVenta = "INSERT INTO venta (id_cliente, total) VALUES (?, ?)";
                PreparedStatement psVenta = con.prepareStatement(sqlVenta, PreparedStatement.RETURN_GENERATED_KEYS);
                psVenta.setInt(1, v.getCliente().getId());
                psVenta.setDouble(2, v.getTotal());
                psVenta.executeUpdate();

                ResultSet rs = psVenta.getGeneratedKeys();
                int idVentaGenerado = 0;
                if (rs.next()) {
                    idVentaGenerado = rs.getInt(1);
                    v.setIdVenta(idVentaGenerado);
                }

                String sqlDetalle = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio, descuento) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);

                for (Producto p : v.getProductos()) {
                    psDetalle.setInt(1, idVentaGenerado);
                    psDetalle.setInt(2, p.getId());
                    psDetalle.setInt(3, 1);
                    psDetalle.setDouble(4, p.getPrecio());
                    psDetalle.setDouble(5, p.calcularDescuento());
                    psDetalle.executeUpdate();
                }

                System.out.println("Venta registrada correctamente con ID: " + idVentaGenerado);
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al registrar venta: " + e.getMessage());
            }
        }
    }

    // 🔹 Listar todas las ventas
    public List<Venta> listarVentas() {
        List<Venta> ventas = new ArrayList<>();
        Connection con = Conexion.conectar();

        if (con != null) {
            try {
                String sql = "SELECT v.id_venta, v.total, c.id AS id_cliente, c.nombre, c.correo " +
                        "FROM venta v JOIN cliente c ON v.id_cliente = c.id";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int idVenta = rs.getInt("id_venta");
                    Cliente cliente = new Cliente(rs.getInt("id_cliente"), rs.getString("nombre"), rs.getString("correo"));
                    Venta venta = new Venta(idVenta, cliente);
                    venta.setProductos(obtenerProductosPorVenta(con, idVenta));
                    venta.calcularTotal();
                    ventas.add(venta);
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al listar ventas: " + e.getMessage());
            }
        }
        return ventas;
    }

    // 🔹 Listar ventas por cliente
    public List<Venta> listarVentasPorCliente(int idCliente) {
        List<Venta> ventas = new ArrayList<>();
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT v.id_venta, v.total, c.id AS id_cliente, c.nombre, c.correo " +
                        "FROM venta v JOIN cliente c ON v.id_cliente = c.id WHERE c.id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, idCliente);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int idVenta = rs.getInt("id_venta");
                    Cliente cliente = new Cliente(rs.getInt("id_cliente"), rs.getString("nombre"), rs.getString("correo"));
                    Venta venta = new Venta(idVenta, cliente);
                    venta.setProductos(obtenerProductosPorVenta(con, idVenta));
                    venta.calcularTotal();
                    ventas.add(venta);
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al listar ventas por cliente: " + e.getMessage());
            }
        }
        return ventas;
    }

    // 🔹 Calcular total general de ventas
    public double calcularTotalGeneral() {
        double totalGeneral = 0;
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT SUM(total) AS total_general FROM venta";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    totalGeneral = rs.getDouble("total_general");
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al calcular total general: " + e.getMessage());
            }
        }
        return totalGeneral;
    }

    // 🔹 Reporte: Ventas por cliente
    public void reporteVentasPorCliente() {
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT c.nombre AS Cliente, COUNT(v.id_venta) AS Cantidad_Ventas, SUM(v.total) AS Total_Comprado " +
                        "FROM venta v JOIN cliente c ON v.id_cliente = c.id GROUP BY c.nombre";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                System.out.println("\n Ventas por cliente:");
                while (rs.next()) {
                    System.out.println("Cliente: " + rs.getString("Cliente") +
                            " | Cantidad de ventas: " + rs.getInt("Cantidad_Ventas") +
                            " | Total comprado: " + rs.getDouble("Total_Comprado"));
                }
                con.close();
            } catch (SQLException e) {
                System.out.println("Error en reporte de ventas por cliente: " + e.getMessage());
            }
        }
    }

    // 🔹 Reporte: Productos más vendidos
    public void reporteProductosMasVendidos() {
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT p.nombre AS Producto, COUNT(d.id_producto) AS Veces_Vendido, SUM(p.precio) AS Total_Ganado " +
                        "FROM detalle_venta d JOIN producto p ON d.id_producto = p.id GROUP BY p.nombre ORDER BY Veces_Vendido DESC";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                System.out.println("\n Productos más vendidos:");
                while (rs.next()) {
                    System.out.println("Producto: " + rs.getString("Producto") +
                            " | Veces vendido: " + rs.getInt("Veces_Vendido") +
                            " | Total ganado: " + rs.getDouble("Total_Ganado"));
                }
                con.close();
            } catch (SQLException e) {
                System.out.println(" Error en reporte de productos más vendidos: " + e.getMessage());
            }
        }
    }

    // 🔹 Reporte: Promedio de ventas
    public void reportePromedioVentas() {
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT AVG(total) AS Promedio_Ventas FROM venta";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("\n Promedio de ventas: " + rs.getDouble("Promedio_Ventas"));
                }
                con.close();
            } catch (SQLException e) {
                System.out.println(" Error en reporte de promedio de ventas: " + e.getMessage());
            }
        }
    }

    // 🔹 Obtener productos de una venta
    private ArrayList<Producto> obtenerProductosPorVenta(Connection con, int idVenta) throws SQLException {
        ArrayList<Producto> productos = new ArrayList<>();
        String sqlDetalle = "SELECT p.id, p.nombre, p.precio, p.tipo " +
                "FROM detalle_venta d JOIN producto p ON d.id_producto = p.id " +
                "WHERE d.id_venta = ?";
        PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
        psDetalle.setInt(1, idVenta);
        ResultSet rsDetalle = psDetalle.executeQuery();

        while (rsDetalle.next()) {
            Producto producto = new Producto(
                    rsDetalle.getInt("id"),
                    rsDetalle.getString("nombre"),
                    rsDetalle.getDouble("precio"),
                    rsDetalle.getString("tipo")
            );
            productos.add(producto);
        }
        return productos;
    }
}
