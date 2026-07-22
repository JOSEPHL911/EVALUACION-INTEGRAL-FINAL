# EVALUACION-INTEGRAL-FINAL

# Sistema de Ventas Académico – Evaluación Integral Final

## Descripción del proyecto
Este proyecto corresponde a la *Evaluación Integral Final del curso Programación Orientada a Objetos*.  
La solución implementa un *sistema de ventas académico* en Java que permite gestionar clientes, productos y ventas, aplicando los principios de POO, colecciones y persistencia de datos en MySQL.

---

## Objetivos
•⁠  ⁠Evidenciar el uso de *clases, objetos y colecciones* en Java.
•⁠  ⁠Aplicar *principios de Programación Orientada a Objetos*: encapsulamiento, herencia, polimorfismo y composición.
•⁠  ⁠Implementar *persistencia de datos* con MySQL y JDBC.
•⁠  ⁠Generar *reportes empresariales*: ventas por cliente, productos más vendidos y promedio de ventas.
•⁠  ⁠Integrar todo en un *repositorio GitHub* con documentación y video de exposición.

---

## Integrantes del equipo
•⁠  ⁠Christopher Reyes Díaz  
•⁠  ⁠Jhonn Paul Ccasani Enciso
•⁠  ⁠Félix Daniel Román Ciprián
•⁠  Joseph Josue Leon Huanca

---

## Tecnologías utilizadas
•⁠  ⁠*Java JDK 21*
•⁠  ⁠*MySQL 8+*
•⁠  ⁠*JDBC (mysql-connector-j)*
•⁠  ⁠*IntelliJ IDEA / Eclipse*
•⁠  ⁠*GitHub* para control de versiones

---

## Estructura del proyecto
ProyectoFinalPOO/
├── SistemaVentaPOO/        # Código fuente Java (src, dao, model, ui)
│   ├── dao/
│   ├── model/
│   ├── ui/
│   └── Conexion.java
├── base_de_datos/          # Script SQL exportado desde MySQL Workbench
│   └── sistema_venta.sql
└── README.md               # Documentación del proyecto

Código

---

## Funcionalidades principales
•⁠  ⁠Registrar clientes y productos.
•⁠  ⁠Registrar ventas con detalle.
•⁠  ⁠Listar ventas generales y por cliente.
•⁠  ⁠Calcular total general de ventas.
•⁠  ⁠Reportes avanzados:
  - Ventas por cliente.
  - Productos más vendidos.
  - Promedio de ventas.

---

## Instalación y ejecución
1.⁠ ⁠Clonar el repositorio:
   ```bash
   git clone https://github.com/[usuario]/ProyectoFinalPOO.git
Configurar la base de datos en MySQL:

bash
mysql -u root -p < base_de_datos/sistema_venta.sql
Configurar credenciales en Conexion.java:

java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_venta";
private static final String USER = "root";
private static final String PASSWORD = "tu_password";
Ejecutar el proyecto desde Main.java.

📊 Ejemplo de salida en consola
Código
Venta registrada correctamente con ID: 27

Listado general de ventas:
Venta [id=27, cliente=Christopher Reyes, total=35.5]
   - ID: 51 | Nombre: El Principito | Precio: 25.5 | Tipo: Libro
   - ID: 52 | Nombre: Cuaderno Norma | Precio: 10.0 | Tipo: Material Escolar

Total general de todas las ventas: 412.95

Ventas por cliente:
Cliente: Christopher Reyes | Cantidad de ventas: 12 | Total comprado: 412.95

Productos más vendidos:
Producto: El Principito | Veces vendido: 12 | Total ganado: 306.0
Producto: Cuaderno Norma | Veces vendido: 12 | Total ganado: 120.0

Promedio de ventas: 34.41
Video de exposición
Enlace al video en YouTube

En el video, todos los integrantes aparecen con cámaras encendidas y explican:

Cómo resolvieron la evaluación.

Por qué llegaron a la solución propuesta.

Qué decisiones tomaron durante el desarrollo.

Conclusión
Este proyecto demuestra la integración de los aprendizajes del curso:

Uso de POO en Java.

Aplicación de colecciones y estructuras de control.

Implementación de persistencia de datos.

Trabajo en equipo y sustentación en video.
