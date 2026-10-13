package com.example.Manga_Monitor.dominio;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TentativaConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "volume_id", nullable = false)
    private Volume volume;
    private LocalDateTime instante;
    private boolean sucesso;
    // Mantém a representação numérica já utilizada no SQLite existente.
    @Enumerated(EnumType.ORDINAL)
    private Disponibilidade disponibilidade;
    private BigDecimal preco;
    private String motivoErro;
    @Enumerated(EnumType.STRING)
    private CondicaoPagamento condicaoPagamento;

    protected TentativaConsulta() { }

    public TentativaConsulta(Volume volume, ResultadoPagina resultado) {
        this.volume = Objects.requireNonNull(volume);
        Objects.requireNonNull(resultado);
        instante = LocalDateTime.now();
        sucesso = true;
        disponibilidade = resultado.getDisponibilidade();
        preco = resultado.getPreco();
        condicaoPagamento = CondicaoPagamento.PIX_NUPAY;
    }

    public static TentativaConsulta falha(Volume volume, String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Uma falha precisa de motivo.");
        }
        TentativaConsulta tentativa = new TentativaConsulta();
        tentativa.volume = Objects.requireNonNull(volume);
        tentativa.instante = LocalDateTime.now();
        tentativa.disponibilidade = Disponibilidade.DESCONHECIDA;
        tentativa.motivoErro = motivo;
        tentativa.condicaoPagamento = CondicaoPagamento.PIX_NUPAY;
        return tentativa;
    }

    public Volume getVolume() { return volume; }
    public LocalDateTime getInstante() { return instante; }
    public boolean isSucesso() { return sucesso; }
    public Disponibilidade getDisponibilidade() { return disponibilidade; }
    public BigDecimal getPreco() { return preco; }
    public String getMotivoErro() { return motivoErro; }
    public CondicaoPagamento getCondicaoPagamento() { return condicaoPagamento; }
}
