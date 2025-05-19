-- Script para crear la tabla CajaAperturas si no existe
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CajaAperturas]') AND type in (N'U'))
BEGIN
    CREATE TABLE [dbo].[CajaAperturas](
        [id] [int] IDENTITY(1,1) NOT NULL,
        [fecha] [date] NOT NULL,
        [hora] [time] NOT NULL,
        [monto_inicial] [decimal](10, 2) NOT NULL,
        [estado] [int] NOT NULL,  -- 1 = Abierta, 0 = Cerrada
        [monto_final] [decimal](10, 2) NULL,
        [fecha_cierre] [date] NULL,
        [hora_cierre] [time] NULL,
        CONSTRAINT [PK_CajaAperturas] PRIMARY KEY CLUSTERED ([id] ASC)
    )
END
GO

-- Índice para mejorar la búsqueda por estado
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name='IX_CajaAperturas_Estado' AND object_id = OBJECT_ID('CajaAperturas'))
BEGIN
    CREATE INDEX [IX_CajaAperturas_Estado] ON [dbo].[CajaAperturas] ([estado])
END
GO
