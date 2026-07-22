package service;

import model.Cliente;
import model.Producto;
import model.Venta;

import java.util.ArrayList;

public class VentaService {
    private ArrayList<Venta> ventas = new ArrayList<>();

    public void registrarVenta(Venta v) {
        ventas.add(v);
    }

    public void listarVentas() {
        for (Venta v : ventas) {
            System.out.println(v);
        }
    }
}
