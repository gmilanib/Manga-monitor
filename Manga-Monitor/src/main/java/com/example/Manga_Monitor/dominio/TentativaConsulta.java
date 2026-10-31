package com.example.Manga_Monitor.dominio;

import com.example.Manga_Monitor.infraestrutura.navegador.NavegadorPlaywright;
import com.example.Manga_Monitor.infraestrutura.navegador.ExtratorPrecoAmazon;
import com.example.Manga_Monitor.infraestrutura.persistencia.DataHoraLegivelConverter;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TentativaConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "volume_id", nullable = false)
    private Volume volume;
    @Column(updatable = false)
    private String titulo;
    @Column(updatable = false)
    private Integer numero;
    @Convert(converter = DataHoraLegivelConverter.class)
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private Disponibilidade disponibilidade;
    private BigDecimal valor;

    protected TentativaConsulta() {
    }

    public TentativaConsulta(Volume volume) {
        this.volume = Objects.requireNonNull(volume, "Volume não pode ser nulo.");
        this.titulo = volume.getTitulo();
        this.numero = volume.getNumero();
        this.data = LocalDateTime.now();
        try (
                NavegadorPlaywright navegadorPlaywright = new NavegadorPlaywright()) {
            Page page = navegadorPlaywright.getPage();
            page.navigate(volume.getURL());
            page.waitForTimeout(2_000);
            Locator continuarComprando = page.getByRole(
                    AriaRole.BUTTON,
                    new Page.GetByRoleOptions()
                            .setName("Continuar comprando")
                            .setExact(true)
            );

            if (continuarComprando.isVisible()) {
                continuarComprando.click();
                page.waitForTimeout(2_000);
                if (!page.url().equals(volume.getURL())) {
                    page.navigate(volume.getURL());
                }

            }
            BigDecimal preco = ExtratorPrecoAmazon.extrair(page);
            if (preco != null) {
                this.disponibilidade = Disponibilidade.DISPONIVEL;
                this.valor = preco;
            } else {
                this.valor = BigDecimal.ZERO;
                this.disponibilidade = Disponibilidade.INDISPONIVEL;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getTitulo() {
        return titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Disponibilidade getDisponibilidade() {
        return disponibilidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
    public Volume getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + "\nTítulo: " + titulo
                + "\nNúmero: " + numero
                + "\nData: " + data
                + "\nDisponibilidade: " + disponibilidade
                + "\nValor: " + valor;
    }
}
