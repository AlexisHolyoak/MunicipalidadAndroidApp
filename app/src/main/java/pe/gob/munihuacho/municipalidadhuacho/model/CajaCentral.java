package pe.gob.munihuacho.municipalidadhuacho.model;

/**
 * Created by peral on 29/03/2017.
 */

public class CajaCentral {
    public String movimiento;
    public String caja;
    public String liq;
    public String nombre;
    public String observacion;
    public String concepto;
    public String importe;
    public String total;
    public String usuario;
    public String fecha;
    public String hora;

    public CajaCentral(){

    }

    public CajaCentral(String movimiento,
                       String caja,
                       String liq,
                       String nombre,
                       String observacion,
                       String concepto,
                       String importe,
                       String total,
                       String usuario,
                       String fecha,
                       String hora) {
        this.movimiento = movimiento;
        this.caja = caja;
        this.liq = liq;
        this.nombre = nombre;
        this.observacion = observacion;
        this.concepto = concepto;
        this.importe = importe;
        this.total = total;
        this.usuario = usuario;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getLiq() {
        return liq;
    }

    public void setLiq(String liq) {
        this.liq = liq;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
