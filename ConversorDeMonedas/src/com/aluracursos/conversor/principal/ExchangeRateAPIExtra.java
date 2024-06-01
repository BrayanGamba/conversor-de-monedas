package com.aluracursos.conversor.principal;

import com.aluracursos.conversor.API.ConsumeAPI;
import com.aluracursos.conversor.archivos.Registros;
import com.aluracursos.conversor.modelos.Moneda;
import com.aluracursos.conversor.modelos.MonedaDTO;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ExchangeRateAPIExtra {

    private static final String API_KEY = "d946d9ecb8ec7f64e3179715";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        ConsumeAPI consumeAPI = new ConsumeAPI();
        Registros registros = new Registros();
        Scanner entrada = new Scanner(System.in);
        int opcion = 0;

        while (true) {
            System.out.println("Ingrese: " +
                    "\n 1 para realizar una conversión " +
                    "\n 2 para consultar registros" +
                    "\n 3 para salir");

            boolean valido = false;
            while (!valido) {
                try {
                    opcion = entrada.nextInt();
                    entrada.nextLine();
                    valido = true;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, elija una opción válida.");
                    entrada.next();
                }
            }

            if (opcion == 1) {
                realizarConversion(entrada, consumeAPI, registros);
            } else if (opcion == 2) {
                Registros.consultarRegistros();
            } else if (opcion == 3) {
                System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
                break;
            } else {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void realizarConversion(Scanner entrada, ConsumeAPI consumeAPI, Registros registros) {
        System.out.println("Ingrese la divisa base (por ejemplo, USD): ");
        String baseCurrency = entrada.nextLine();
        System.out.println("Ingrese la divisa destino (por ejemplo, USD): ");
        String targetCurrency = entrada.nextLine();
        System.out.println("Ingrese el valor que deseas convertir:");
        String conversion = "";
        double valor = 0;
        boolean valido = false;
        while (!valido) {
            try {
                valor = entrada.nextDouble();
                entrada.nextLine();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, elija una opción válida.");
                entrada.next();
            }
        }

        try {
            String response = consumeAPI.connectAPI(API_URL + baseCurrency);
            MonedaDTO monedaDTO = consumeAPI.parseJson(response);

            if (monedaDTO != null) {
                for (Map.Entry<String, Double> entry : monedaDTO.getConversionRates().entrySet()) {
                    Moneda moneda = new Moneda(entry.getKey(), entry.getValue());
                    if (moneda.getNombre().equals(targetCurrency)){
                        conversion = "El valor " + valor + " [" + baseCurrency + "] "
                                + "corresponde al valor final de =>>> " + valor * moneda.getTasaDeCambio()
                                + " [" + targetCurrency + "]";
                        System.out.println(registros.getDATE());
                        System.out.println(conversion);
                        break;
                    }
                }
                Registros.registrarConversion(conversion);
            } else {
                System.out.println("Error al obtener los datos de la com.aluracursos.conversor.API.");
                System.out.println("Por favor revise que las divisas sean correctas.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error al obtener los datos de la com.aluracursos.conversor.API.");
            System.out.println("Por favor revise que las divisas sean correctas.");
        }
    }
}
