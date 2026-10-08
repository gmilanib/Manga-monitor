package com.example.Manga_Monitor.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private int numero;
    private String URL;

    public Volume() {
    }

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
       URL = URL.replaceAll("\"", "\\\"");
        return URL;
    }

}
