package com.aluracursos.conversor.modelos;

public class Moneda {
    private String nombre;
    private double tasaDeCambio;

    public Moneda(String nombre, double tasaDeCambio) {
        this.nombre = nombre;
        this.tasaDeCambio = tasaDeCambio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTasaDeCambio() {
        return tasaDeCambio;
    }

    public void setTasaDeCambio(double tasaDeCambio) {
        this.tasaDeCambio = tasaDeCambio;
    }
}
