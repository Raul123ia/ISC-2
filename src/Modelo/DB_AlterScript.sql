-- Script para modificar la tabla CajaCierres y añadir la columna diferencia
-- Ejecutar este script si la tabla ya existe y necesita ser actualizada

-- Verificar si la columna ya existe antes de añadirla
IF NOT EXISTS (
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'CajaCierres' AND COLUMN_NAME = 'diferencia'
)
BEGIN
    -- Añadir la columna diferencia
    ALTER TABLE CajaCierres ADD diferencia DECIMAL(10, 2) NULL;
    
    -- Actualizar registros existentes poniendo diferencia en 0.00
    UPDATE CajaCierres SET diferencia = 0.00 WHERE diferencia IS NULL;
    
    -- Cambiar la columna para que no permita valores nulos después de la actualización
    ALTER TABLE CajaCierres ALTER COLUMN diferencia DECIMAL(10, 2) NOT NULL;
    
    PRINT 'Columna diferencia añadida correctamente a la tabla CajaCierres';
END
ELSE
BEGIN
    PRINT 'La columna diferencia ya existe en la tabla CajaCierres';
END
