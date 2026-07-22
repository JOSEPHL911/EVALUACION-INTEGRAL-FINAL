package dao;

import model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO {

    public int registrarCliente(Cliente c) {
        int idGenerado = 0;
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                // Evitar duplicados por correo
                if (existeCliente(c.getCorreo())) {
                    System.out.println(" Cliente ya existe con correo: " + c.getCorreo());
                    return 0;
                }

                String sql = "INSERT INTO cliente (nombre, correo) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getCorreo());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                    c.setId(idGenerado);
                }

                System.out.println(" Cliente registrado correctamente con ID: " + idGenerado);
                con.close();
            } catch (SQLException e) {
                System.out.println(" Error al registrar cliente: " + e.getMessage());
            }
        }
        return idGenerado;
    }

    public boolean existeCliente(String correo) {
        boolean existe = false;
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT COUNT(*) FROM cliente WHERE correo = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, correo);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    existe = true;
                }
                con.close();
            } catch (SQLException e) {
                System.out.println(" Error al verificar cliente: " + e.getMessage());
            }
        }
        return existe;
    }

    // 🔹 Recuperar ID del cliente existente por correo
    public int obtenerIdPorCorreo(String correo) {
        int idCliente = 0;
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT id FROM cliente WHERE correo = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, correo);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idCliente = rs.getInt("id");
                }
                con.close();
            } catch (SQLException e) {
                System.out.println(" Error al obtener ID del cliente: " + e.getMessage());
            }
        }
        return idCliente;
    }

    public ArrayList<Cliente> listarClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        Connection con = Conexion.conectar();
        if (con != null) {
            try {
                String sql = "SELECT * FROM cliente";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Cliente c = new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"));
                    clientes.add(c);
                }
                con.close();
            } catch (SQLException e) {
                System.out.println(" Error al listar clientes: " + e.getMessage());
            }
        }
        return clientes;
    }
}
