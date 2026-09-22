package com.example.Manga_Monitor.dominio;

public class Volume {
    private final String titulo;
    private final int numero;
    private final String URL;

    public Volume(String titulo, int numero, String URL) {
        if (titulo == null) {
            throw new NullPointerException("Título nao pode ser nulo.");
        } else {
            if (titulo.trim().equals("")) {
                throw new IllegalArgumentException("Título nao pode ser vazio.");
            } else {
                this.titulo = titulo;
            }
        }

        if (URL == null) {
            throw new NullPointerException("URL nao pode ser nulo.");
        } else {
            if (URL.trim().equals("")) {
                throw new IllegalArgumentException("URL nao pode ser vazio.");
            } else {
                this.URL = URL;
            }
        }

        if (numero > 0) {
            this.numero = numero;
        } else {
            throw new IllegalArgumentException("Número nao pode ser vazio");
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public int getNumero() {
        return numero;
    }

    public String getURL() {
        return URL;
    }

}
