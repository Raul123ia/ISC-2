# Instrucciones para la Tabla MovimientosInventario

## Descripción
La tabla `MovimientosInventario` es necesaria para registrar todos los movimientos de entrada y salida de productos en el inventario. Esto permite mantener un registro detallado de cada cambio en el stock.

## Estructura de la Tabla
La tabla contiene los siguientes campos:
- `id`: Identificador único del movimiento (texto)
- `idProducto`: ID del producto afectado (texto)
- `tipo`: Tipo de movimiento ("ENTRADA", "SALIDA", "AJUSTE")
- `cantidad`: Cantidad de productos movidos (número entero)
- `fecha`: Fecha y hora del movimiento
- `referencia`: Referencia adicional como número de venta, compra, etc.

## Pasos para Crear la Tabla

### Opción 1: Usando el Script SQL
1. Abrir el archivo `DB_MovimientosInventario.sql` ubicado en la carpeta `src/Modelo/`
2. Ejecutar este script en su gestor de base de datos SQL Server

### Opción 2: Ejecución Manual
Si prefiere crear la tabla manualmente, puede ejecutar el siguiente código SQL:

```sql
CREATE TABLE MovimientosInventario (
    id VARCHAR(20) PRIMARY KEY,
    idProducto VARCHAR(20) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    fecha DATETIME NOT NULL,
    referencia VARCHAR(100),
    FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
);
