public class Valor {
    private double resultado;
    private String monedaDestino;

    public Valor(double cantidad, String monedaDestino, NumeroApi datosApi) {
        this.monedaDestino = monedaDestino;
        this.resultado = cantidad * datosApi.conversion_rates().get(monedaDestino);
    }

    public String mostrarResultado() {
        return "Resultado: " + resultado + " " + monedaDestino;
    }
}
