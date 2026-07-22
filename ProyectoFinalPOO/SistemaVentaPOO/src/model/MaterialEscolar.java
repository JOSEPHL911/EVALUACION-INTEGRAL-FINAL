package model;

public class MaterialEscolar extends Producto {
    private String marca;

    public MaterialEscolar(int id, String nombre, double precio, String marca) {
        super(id, nombre, precio, "Material Escolar");
        this.marca = marca;
    }

    @Override
    public double calcularDescuento() {
        return getPrecio() * 0.05; // 5% de descuento para materiales escolares
    }

    @Override
    public String toString() {
        return super.toString() + " | Marca: " + marca;
    }
}
