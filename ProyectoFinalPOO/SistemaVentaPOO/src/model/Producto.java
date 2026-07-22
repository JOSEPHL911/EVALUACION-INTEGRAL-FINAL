package model;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String tipo;

    public Producto(int id, String nombre, double precio, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    //  Setter para asignar el ID generado por MySQL
    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getTipo() { return tipo; }

    public double calcularDescuento() {
        return 0; // Por defecto sin descuento
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Precio: " + precio + " | Tipo: " + tipo;
    }

    //  Evita duplicados al comparar productos
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto p = (Producto) obj;
        return id == p.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
