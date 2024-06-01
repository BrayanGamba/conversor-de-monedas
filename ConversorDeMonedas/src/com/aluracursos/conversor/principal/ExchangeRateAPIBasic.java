package com.aluracursos.conversor.principal;

import com.aluracursos.conversor.API.ConsumeAPI;
import com.aluracursos.conversor.modelos.Moneda;
import com.aluracursos.conversor.modelos.MonedaDTO;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ExchangeRateAPIBasic {

    private static final String API_KEY = "d946d9ecb8ec7f64e3179715";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {

        ConsumeAPI consumeAPI = new ConsumeAPI();
        Scanner entrada = new Scanner(System.in);
        boolean salir = false;
        int dig = 0;
        String divisaBase = "USD";
        String divisaDestino = "";

        while (!salir) {
            System.out.println("**************************************************");
            System.out.println("===CONVERSIONES===");
            System.out.println("Sea bienvenido/a al Conversor de com.aluracursos.conversor.modelos.Moneda =]");
            System.out.println("Presione 1 para convertir de Dólar =>> Peso argentino");
            System.out.println("Presione 2 para convertir de Peso argentino =>> Dólar");
            System.out.println("Presione 3 para convertir de Dólar =>> Real brasileño");
            System.out.println("Presione 4 para convertir de Real brasileño =>> Dólar");
            System.out.println("Presione 5 para convertir de Dólar =>> Peso colombiano");
            System.out.println("Presione 6 para convertir de Peso colombiano =>> Dólar");
            System.out.println("Presione 7 para salir.");
            System.out.println("Elija una opción válida:");
            System.out.println("**************************************************");
            boolean valido = false;
            while (!valido) {
                try {
                    dig = entrada.nextInt();
                    valido = true;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, elija una opción válida.");
                    entrada.next();
                }
            }

            if (dig == 7) {
                salir = true;
                System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
            } else {
                switch (dig) {
                    case 1 -> divisaDestino = "ARS";
                    case 2 -> {
                        divisaBase = "ARS";
                        divisaDestino = "USD";
                    }
                    case 3 -> divisaDestino = "BRL";
                    case 4 -> {
                        divisaBase = "BRL";
                        divisaDestino = "USD";
                    }
                    case 5 -> divisaDestino = "COP";
                    case 6 -> {
                        divisaBase = "COP";
                        divisaDestino = "USD";
                    }
                }
                System.out.println("Ingrese el valor que deseas convertir:");
                valido = false;
                double valor = 0;
                while (!valido) {
                    try {
                        valor = entrada.nextDouble();
                        valido = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida. Por favor, ingrese un valor válido.");
                        entrada.next();
                    }
                }

                try {
                    String response = consumeAPI.connectAPI(API_URL + divisaBase);
                    MonedaDTO monedaDTO = consumeAPI.parseJson(response);

                    if (monedaDTO != null) {
                        for (Map.Entry<String, Double> entry : monedaDTO.getConversionRates().entrySet()) {
                            Moneda moneda = new Moneda(entry.getKey(), entry.getValue());
                            if (moneda.getNombre().equals(divisaDestino)){
                                System.out.println("El valor " +valor+ " [" + divisaBase + "] "
                                        + "corresponde al valor final de =>>> " + valor*moneda.getTasaDeCambio()
                                        +" [" + divisaDestino + "]");
                            }
                        }
                    } else {
                        System.out.println("Error al obtener los datos de la com.aluracursos.conversor.API.");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

}