-- Script para agregar campos activo a las tablas

-- Agregar campo activo a la tabla Productos
ALTER TABLE Productos ADD activo BIT DEFAULT 1;

-- Agregar campo activo a la tabla Categorias
ALTER TABLE Categorias ADD activo BIT DEFAULT 1;

-- Agregar campo activo a la tabla Proveedores
ALTER TABLE Proveedores ADD activo BIT DEFAULT 1;

-- Inicializar todos los registros existentes como activos
UPDATE Productos SET activo = 1 WHERE activo IS NULL;
UPDATE Categorias SET activo = 1 WHERE activo IS NULL;
UPDATE Proveedores SET activo = 1 WHERE activo IS NULL;
