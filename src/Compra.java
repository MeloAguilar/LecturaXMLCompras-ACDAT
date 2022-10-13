public class Compra {
    private String fecha;
    private double valorTotal;

    private int cantidadProductos;

    private double totalDescuentos;




    public Compra(String fecha) {
        this.fecha = fecha;
        this.valorTotal = 0.0;
        this.totalDescuentos = 0.0;
        this.cantidadProductos = 0;
    }

    public Compra(){
        this.valorTotal = 0.0;
        this.totalDescuentos = 0.0;
        this.cantidadProductos = 0;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void aumentarValorTotal(Double valor){
        this.valorTotal += valor;
    }

    public String getFecha() {
        return fecha;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void aumentarCantidadProductos()
    {
        this.cantidadProductos++;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void sumarDescuento(Double descuento){
        this.totalDescuentos += descuento;
    }

    public double getTotalDescuentos() {
        return totalDescuentos;
    }
}
