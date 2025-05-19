CREATE TABLE CajaCierres (
    id INT IDENTITY(1,1) PRIMARY KEY,
    fecha VARCHAR(10) NOT NULL,
    hora VARCHAR(8) NOT NULL,
    cajero VARCHAR(50) NOT NULL,
    fondoInicial DECIMAL(10, 2) NOT NULL,
    fondoFinal DECIMAL(10, 2) NOT NULL,
    ventasEfectivo DECIMAL(10, 2) NOT NULL,
    ventasCredito DECIMAL(10, 2) NOT NULL,
    ventasDebito DECIMAL(10, 2) NOT NULL,
    ventasTransferencia DECIMAL(10, 2) NOT NULL,
    totalVentas DECIMAL(10, 2) NOT NULL,
    efectivoCierre DECIMAL(10, 2) NOT NULL,
    diferencia DECIMAL(10, 2) NOT NULL
);
