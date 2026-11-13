package com.inventario.flujo.config;

import com.inventario.flujo.model.*;
import com.inventario.flujo.repository.MovimientoCajaRepository;
import com.inventario.flujo.repository.ProductoRepository;
import com.inventario.flujo.repository.ProveedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoCajaRepository movimientoCajaRepository;

    public DataLoader(ProveedorRepository proveedorRepository,
                      ProductoRepository productoRepository,
                      MovimientoCajaRepository movimientoCajaRepository) {
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
        this.movimientoCajaRepository = movimientoCajaRepository;
    }

    @Override
    public void run(String... args) {
        if (productoRepository.count() > 0) {
            return;
        }

        Proveedor p1 = proveedor("TechDistribuidora SAS", "Ana Ruiz", "ana@techdist.co", "3001112233", 5, 5);
        Proveedor p2 = proveedor("CompuMayorista", "Carlos Méndez", "ventas@compumayor.co", "3002223344", 7, 4);
        Proveedor p3 = proveedor("SoftLicencias Andina", "Laura Gómez", "laura@softandina.co", "3003334455", 2, 4);
        Proveedor p4 = proveedor("ServerParts Latam", "Diego Torres", "diego@serverparts.co", "3004445566", 10, 3);
        Proveedor p5 = proveedor("Energía UPS Colombia", "María López", "maria@energiacups.co", "3005556677", 8, 5);
        proveedorRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        List<Producto> productos = List.of(
                prod("NB-DELL-001", "Laptop Dell Latitude 5540", "Dell", "Portátiles", "Empresarial",
                        3200000, 4299000, 4, 5, 12, 25, p1),
                prod("NB-HP-002", "Laptop HP ProBook 450", "HP", "Portátiles", "Empresarial",
                        2800000, 3799000, 2, 4, 10, 40, p1),
                prod("DT-LEN-003", "Desktop Lenovo ThinkCentre", "Lenovo", "Computadores", "Escritorio",
                        2100000, 2899000, 8, 3, 10, 60, p2),
                prod("MON-SAM-004", "Monitor Samsung 27\" IPS", "Samsung", "Periféricos", "Monitores",
                        650000, 899000, 15, 6, 20, 35, p2),
                prod("SSD-KING-005", "SSD Kingston NV2 1TB", "Kingston", "Componentes", "Almacenamiento",
                        280000, 399000, 3, 10, 30, 20, p2),
                prod("RAM-COR-006", "Memoria Corsair 16GB DDR4", "Corsair", "Componentes", "Memoria",
                        220000, 319000, 25, 8, 40, 15, p2),
                prod("SW-OFF-007", "Microsoft 365 Business", "Microsoft", "Software", "Productividad",
                        180000, 249000, 50, 20, 80, 10, p3),
                prod("SW-AVG-008", "Antivirus AVG Business", "AVG", "Software", "Seguridad",
                        90000, 149000, 12, 15, 40, 95, p3),
                prod("SRV-DEL-009", "Servidor Dell PowerEdge R350", "Dell", "Servidores", "Rack",
                        12500000, 15990000, 1, 1, 3, 120, p4),
                prod("SRV-HP-010", "Servidor HPE ProLiant DL20", "HPE", "Servidores", "Rack",
                        9800000, 12990000, 0, 1, 2, 45, p4),
                prod("UPS-APC-011", "UPS APC 1500VA", "APC", "UPS", "Backup",
                        890000, 1199000, 6, 4, 12, 70, p5),
                prod("UPS-TRI-012", "UPS Tripp Lite 1000VA", "Tripp Lite", "UPS", "Backup",
                        620000, 849000, 2, 3, 8, 100, p5),
                prod("KB-LOG-013", "Teclado Logitech MX Keys", "Logitech", "Periféricos", "Entrada",
                        320000, 449000, 18, 5, 25, 30, p1),
                prod("MS-LOG-014", "Mouse Logitech MX Master 3S", "Logitech", "Periféricos", "Entrada",
                        280000, 399000, 22, 6, 30, 18, p1),
                prod("NW-CIS-015", "Switch Cisco 24 puertos", "Cisco", "Redes", "Switching",
                        2100000, 2799000, 3, 2, 6, 85, p4),
                prod("NW-UBI-016", "Access Point Ubiquiti WiFi 6", "Ubiquiti", "Redes", "Wireless",
                        450000, 629000, 1, 4, 10, 55, p4),
                prod("CAB-CAT-017", "Cable Cat6 caja 305m", "Generic", "Redes", "Cableado",
                        380000, 499000, 9, 3, 12, 40, p2),
                prod("PRT-EPS-018", "Impresora Epson EcoTank L3250", "Epson", "Periféricos", "Impresión",
                        780000, 999000, 5, 2, 8, 110, p2),
                prod("WEB-LOG-019", "Webcam Logitech C920", "Logitech", "Periféricos", "Video",
                        260000, 369000, 0, 5, 15, 22, p1),
                prod("HDD-WD-020", "Disco WD Red 4TB NAS", "Western Digital", "Componentes", "Almacenamiento",
                        520000, 699000, 7, 4, 15, 130, p4),
                prod("GPU-ASU-021", "GPU ASUS Dual RTX 4060", "ASUS", "Componentes", "Video",
                        1450000, 1899000, 2, 2, 6, 48, p2),
                prod("CASE-COR-022", "Chasis Corsair 4000D", "Corsair", "Componentes", "Gabinete",
                        410000, 549000, 11, 3, 12, 75, p2)
        );
        productoRepository.saveAll(productos);

        LocalDate base = LocalDate.now().withDayOfMonth(1);
        movimientoCajaRepository.saveAll(List.of(
                mov(TipoMovimiento.INGRESO, "Ventas portátiles", bd("28500000"), base.plusDays(2)),
                mov(TipoMovimiento.INGRESO, "Ventas componentes", bd("12450000"), base.plusDays(5)),
                mov(TipoMovimiento.INGRESO, "Licencias software", bd("5600000"), base.plusDays(8)),
                mov(TipoMovimiento.INGRESO, "Servicios ensamble", bd("3200000"), base.plusDays(12)),
                mov(TipoMovimiento.GASTO, "Pago proveedor TechDistribuidora", bd("9800000"), base.plusDays(3)),
                mov(TipoMovimiento.GASTO, "Pago ServerParts", bd("7200000"), base.plusDays(7)),
                mov(TipoMovimiento.GASTO, "Arriendo bodega", bd("4500000"), base.plusDays(1)),
                mov(TipoMovimiento.GASTO, "Servicios públicos", bd("890000"), base.plusDays(10)),
                mov(TipoMovimiento.GASTO, "Transporte última milla", bd("1250000"), base.plusDays(14))
        ));
    }

    private Proveedor proveedor(String nombre, String contacto, String email, String tel, int lead, int cal) {
        Proveedor p = new Proveedor();
        p.setNombre(nombre);
        p.setContacto(contacto);
        p.setEmail(email);
        p.setTelefono(tel);
        p.setLeadTimeDias(lead);
        p.setCalificacionCosto(cal);
        p.setActivo(true);
        return p;
    }

    private Producto prod(String sku, String nombre, String marca, String cat, String sub,
                          long costo, long precio, int stock, int min, int ideal, int dias, Proveedor prov) {
        Producto p = new Producto();
        p.setSku(sku);
        p.setNombre(nombre);
        p.setMarca(marca);
        p.setCategoria(cat);
        p.setSubcategoria(sub);
        p.setCostoUnitario(bd(String.valueOf(costo)));
        p.setPrecioVenta(bd(String.valueOf(precio)));
        p.setStockActual(stock);
        p.setStockMinimo(min);
        p.setStockIdeal(ideal);
        p.setDiasEnInventario(dias);
        p.setProveedor(prov);
        return p;
    }

    private MovimientoCaja mov(TipoMovimiento tipo, String concepto, BigDecimal monto, LocalDate fecha) {
        MovimientoCaja m = new MovimientoCaja();
        m.setTipo(tipo);
        m.setConcepto(concepto);
        m.setMonto(monto);
        m.setFecha(fecha);
        return m;
    }

    private BigDecimal bd(String v) {
        return new BigDecimal(v);
    }
}
