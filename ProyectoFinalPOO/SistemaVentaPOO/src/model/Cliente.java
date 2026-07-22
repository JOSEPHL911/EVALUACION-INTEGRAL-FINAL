package model;

public class Cliente {
    private int id;
    private String nombre;
    private String correo;

    // Constructor con ID (para clientes existentes)
    public Cliente(int id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Constructor sin ID (para nuevos clientes)
    public Cliente(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", correo=" + correo + "]";
    }
}
