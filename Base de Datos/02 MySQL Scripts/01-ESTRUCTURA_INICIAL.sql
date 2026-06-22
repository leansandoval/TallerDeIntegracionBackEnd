-- 1. CREACIÓN E INICIALIZACIÓN DE LA BASE DE DATOS
CREATE DATABASE IF NOT EXISTS TPIntegracion;
USE TPIntegracion;

-- 2. ELIMINACIÓN DE TABLAS PREVIAS (En orden inverso por las FK)
DROP TABLE IF EXISTS LineaDeVenta;
DROP TABLE IF EXISTS Venta;
DROP TABLE IF EXISTS Producto;

-- 3. CREACIÓN DE TABLAS MAESTRAS (No dependen de nadie)

-- Tabla: Producto
CREATE TABLE Producto (
    codigo VARCHAR(20) NOT NULL,
    descripcion VARCHAR(100),
    stock INT,
    activo BIT,
    precio DECIMAL(15,2),
    CONSTRAINT PK_Producto PRIMARY KEY (codigo)
);

-- Tabla: Venta
CREATE TABLE Venta (
    id INT NOT NULL AUTO_INCREMENT,
    fecha DATETIME,
    cliente VARCHAR(200),
    total DECIMAL(15,2),
    rechazada BIT, -- 0 = válida, 1 = rechazada (Agregado del DER)
    CONSTRAINT PK_Venta PRIMARY KEY (id)
);

-- 4. CREACIÓN DE TABLAS DEPENDIENTES (Dependen de Producto y Venta)

-- Tabla: LineaDeVenta
CREATE TABLE LineaDeVenta (
    id INT NOT NULL AUTO_INCREMENT,
    ventaID INT NOT NULL,
    codigoProducto VARCHAR(20) NOT NULL,
    cantidad INT, 
    precioUnitario DECIMAL(15,2),
    subtotal DECIMAL(15,2),
    CONSTRAINT PK_LineaDeVenta PRIMARY KEY (id),
    CONSTRAINT FK_LDV_Venta FOREIGN KEY (ventaID) REFERENCES Venta(id) ON DELETE CASCADE,
    CONSTRAINT FK_LDV_Producto FOREIGN KEY (codigoProducto) REFERENCES Producto(codigo)
);

-- 5. VERIFICACIÓN OPERATIVA
SELECT 'Tablas creadas con éxito' AS Estado;