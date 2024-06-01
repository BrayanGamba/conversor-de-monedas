package com.aluracursos.conversor.archivos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Registros {

    private static final String FILE_NAME = "registro.txt";
    private static String DATE = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

    public static void registrarConversion(String conversion) {
        try (FileWriter escritura = new FileWriter(FILE_NAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(escritura)) {
            bufferedWriter.write(DATE + " --> " + conversion);
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("Error al registrar la conversión: " + e.getMessage());
        }
    }

    public static void consultarRegistros() {
        Scanner entrada = new Scanner(System.in);
        String fechaConsulta = "";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaIngresada = null;

        while (fechaIngresada == null) {
            System.out.println("Ingrese la fecha para consultar los registros (dd-MM-yyyy): ");
            fechaConsulta = entrada.nextLine();
            try {
                fechaIngresada = LocalDate.parse(fechaConsulta, formato);
            } catch (DateTimeParseException e) {
                System.out.println("Error: La fecha ingresada no está en el formato correcto. Por favor, intente de nuevo.");
            }
        }
        String fecha = fechaIngresada.format(formato);

        try {
            List<String> registros = Files.readAllLines(Paths.get(FILE_NAME));
            boolean tieneRegistros = false;
            for (String registro : registros) {
                if (registro.contains(fecha)) {
                    System.out.println(registro);
                    tieneRegistros = true;
                }
            }
            if (!tieneRegistros) {
                System.out.println("No se encontraron registros para la fecha especificada.");
            }
        } catch (IOException e) {
            System.out.println("Error al consultar los registros: " + e.getMessage());
        }
    }

    public String getDATE() {
        return DATE;
    }
}
