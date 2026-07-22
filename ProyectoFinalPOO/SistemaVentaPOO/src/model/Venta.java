package model;

import java.util.ArrayList;

public class Venta {
    private int idVenta;
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private double total;

    public Venta(int idVenta, Cliente cliente) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.productos = new ArrayList<>();
        this.total = 0;
    }

    //  Evita duplicados y recalcula total
    public void agregarProducto(Producto p) {
        if (!productos.contains(p)) {
            productos.add(p);
            calcularTotal();
        } else {
            System.out.println(" El producto '" + p.getNombre() + "' ya está en la venta.");
        }
    }

    //  Recalcula el total correctamente
    public void calcularTotal() {
        double nuevoTotal = 0;
        for (Producto p : productos) {
            nuevoTotal += p.getPrecio() - p.calcularDescuento();
        }
        this.total = nuevoTotal;
    }

    //  Getters y Setters
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public Cliente getCliente() { return cliente; }
    public ArrayList<Producto> getProductos() { return productos; }
    public void setProductos(ArrayList<Producto> productos) { this.productos = productos; calcularTotal(); }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return "Venta [id=" + idVenta + ", cliente=" + cliente.getNombre() + ", total=" + total + "]";
    }
}
