package com.example.Manga_Monitor.dominio;

import java.math.BigDecimal;
import java.util.Objects;

/** Oferta principal consultada; preço ausente não significa falta de estoque. */
public final class ResultadoPagina {
    private final Disponibilidade disponibilidade;
    private final BigDecimal preco;

    public ResultadoPagina(Disponibilidade disponibilidade, BigDecimal preco) {
        this.disponibilidade = Objects.requireNonNull(disponibilidade);
        if (preco != null && preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser positivo ou ausente.");
        }
        if (disponibilidade == Disponibilidade.INDISPONIVEL && preco != null) {
            throw new IllegalArgumentException("Produto sem estoque não deve ter preço registrado.");
        }
        this.preco = preco;
    }

    public Disponibilidade getDisponibilidade() { return disponibilidade; }
    public BigDecimal getPreco() { return preco; }
}
