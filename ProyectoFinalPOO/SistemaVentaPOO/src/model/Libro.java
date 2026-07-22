package model;

public class Libro extends Producto {
    private String autor;

    public Libro(int id, String nombre, double precio, String autor) {
        super(id, nombre, precio, "Libro");
        this.autor = autor;
    }

    @Override
    public double calcularDescuento() {
        return getPrecio() * 0.10; // 10% de descuento para libros
    }

    @Override
    public String toString() {
        return super.toString() + " | Autor: " + autor;
    }
}
