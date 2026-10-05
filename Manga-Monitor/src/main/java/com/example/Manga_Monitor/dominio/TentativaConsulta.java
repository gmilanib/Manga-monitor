package com.example.Manga_Monitor.dominio;

import com.example.Manga_Monitor.infraestrutura.http.ConsultorPaginaHttp;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
public class TentativaConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Volume volume;
    private LocalDateTime instante;
    private boolean sucesso;
    private Disponibilidade disponibilidade;
    private BigDecimal preco;
    private String motivoErro;

    public TentativaConsulta() {
    }

    public TentativaConsulta(Volume volume, BigDecimal preco, String motivoErro) {
        this.volume = volume;
        this.instante = LocalDateTime.now();
        this.sucesso = Objects.equals(motivoErro, "");
        if (this.sucesso) {
            this.preco = preco;
            if (this.preco.compareTo(BigDecimal.ZERO) > 0) {
                this.disponibilidade = Disponibilidade.DISPONIVEL;
            } else {
                this.disponibilidade = Disponibilidade.INDISPONIVEL;
            }
        } else {
            this.disponibilidade = Disponibilidade.DESCONHECIDA;
            this.motivoErro = motivoErro;
        }
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