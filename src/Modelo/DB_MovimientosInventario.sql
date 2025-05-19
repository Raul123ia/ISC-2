-- Script para crear la tabla MovimientosInventario
-- Esta tabla registra todos los movimientos de entrada y salida del inventario

-- Verificar si la tabla ya existe antes de crearla
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'MovimientosInventario')
BEGIN
    -- Crear la tabla de MovimientosInventario
    CREATE TABLE MovimientosInventario (
        id VARCHAR(50) PRIMARY KEY,            -- Identificador único del movimiento
        idProducto VARCHAR(50) NOT NULL,       -- ID del producto afectado
        tipo VARCHAR(50) NOT NULL,             -- Tipo de movimiento (ENTRADA, SALIDA, AJUSTE)
        cantidad INT NOT NULL,                 -- Cantidad de productos movidos
        fecha DATETIME NOT NULL,               -- Fecha y hora del movimiento
        referencia VARCHAR(100),               -- Referencia (ej: número de venta, compra, etc.)
        FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
    );
    
    PRINT 'Tabla MovimientosInventario creada correctamente';
END
ELSE
BEGIN
    PRINT 'La tabla MovimientosInventario ya existe';
END
