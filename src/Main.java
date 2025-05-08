import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Seleccione una opción:");
            System.out.println("1) USD -> ARS");
            System.out.println("2) ARS -> USD");
            System.out.println("3) USD -> BRL");
            System.out.println("4) BRL -> USD");
            System.out.println("5) Salir");

            int opcion = scanner.nextInt();
            if (opcion == 5) return;

            System.out.print("Ingrese el valor a convertir: ");
            double valor = scanner.nextDouble();

            String base = "";
            String destino = "";

            switch (opcion) {
                case 1 -> { base = "USD"; destino = "ARS"; }
                case 2 -> { base = "ARS"; destino = "USD"; }
                case 3 -> { base = "USD"; destino = "BRL"; }
                case 4 -> { base = "BRL"; destino = "USD"; }
                default -> {
                    System.out.println("Opción inválida."); return;
                }
            }

            try {
                String url = "https://v6.exchangerate-api.com/v6/5fd930370d4e07ef0b6ccb42/latest/" + base;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                Gson gson = new GsonBuilder().create();
                NumeroApi datosApi = gson.fromJson(response.body(), NumeroApi.class);

                Map<String, Double> tasas = datosApi.conversion_rates();
                double tasa = tasas.get(destino);
                double resultado = valor * tasa;

                System.out.printf("Resultado: %.2f %s = %.2f %s\n", valor, base, resultado, destino);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }
}
