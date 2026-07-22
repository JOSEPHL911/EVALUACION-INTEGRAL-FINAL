package dao;

import model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductoDAO {

    public int registrarProducto(Producto p) {
        int idGenerado = 0;
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "INSERT INTO producto (nombre, precio, tipo) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, p.getNombre());
                ps.setDouble(2, p.getPrecio());
                ps.setString(3, p.getTipo());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    p.setId(idGenerado);
                }

                System.out.println(" Producto registrado correctamente con ID: " + idGenerado);
                con.close();
            } catch (SQLException e) {
                System.out.println(" Error al registrar producto: " + e.getMessage());
            }
        }
        return idGenerado;
    }

    public ArrayList<Producto> listarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT * FROM producto";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Producto p = new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("tipo"));
                    productos.add(p);
                }
                con.close();
            } catch (SQLException e) {
                System.out.println(" Error al listar productos: " + e.getMessage());
            }
        }
        return productos;
    }
}
