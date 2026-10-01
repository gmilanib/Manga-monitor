package com.example.Manga_Monitor.dominio;

import com.example.Manga_Monitor.infraestrutura.http.ConsultorPaginaHttp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TentativaConsulta {
    private Volume volume;
    private LocalDateTime instante;
    private boolean sucesso;
    private Disponibilidade disponibilidade;
    private BigDecimal preco;
    private String motivoErro;

    public TentativaConsulta(Volume volume, LocalDateTime instante, boolean sucesso, Disponibilidade disponibilidade, BigDecimal preco, String motivoErro) {
        this.volume = volume;
        this.instante = instante;
        this.sucesso = sucesso;
        this.disponibilidade = disponibilidade;
        this.preco = preco;
        this.motivoErro = motivoErro;
    }

    public Volume getVolume() {
        return volume;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public Disponibilidade getDisponibilidade() {
        return disponibilidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getMotivoErro() {
        return motivoErro;
    }


    public void tentativaConsulta() {
        this.instante = LocalDateTime.now();
        ConsultorPaginaHttp consultorPaginaHttp = new ConsultorPaginaHttp();

    }
}