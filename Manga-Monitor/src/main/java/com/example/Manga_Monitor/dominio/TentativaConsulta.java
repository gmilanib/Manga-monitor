package com.example.Manga_Monitor.dominio;

import com.example.Manga_Monitor.infraestrutura.navegador.NavegadorPlaywright;
import com.example.Manga_Monitor.infraestrutura.persistencia.DataHoraLegivelConverter;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
public class TentativaConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "volume_id", nullable = false)
    private Volume volume;
    @Convert(converter = DataHoraLegivelConverter.class)
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private Disponibilidade disponibilidade;
    private BigDecimal valor;

    protected TentativaConsulta() {
    }

    public TentativaConsulta(Volume volume) {
        this.volume = volume;
        this.data = LocalDateTime.now();

        NavegadorPlaywright navegadorPlaywright = new NavegadorPlaywright();
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
        Locator preco = page.locator("#corePriceDisplay_desktop_feature_div .priceToPay");
        if (preco.isVisible()) {
            this.disponibilidade = Disponibilidade.DISPONIVEL;
            String parteInteira = preco
                    .locator(".a-price-whole")
                    .innerText()
                    .replace(".", "")
                    .replace(",", "")
                    .trim();
            String parteDecimal = preco
                    .locator(".a-price-decimal")
                    .innerText()
                    .replace(".", "")
                    .replace(",", "")
                    .trim();

            this.valor = new BigDecimal(parteInteira + "." + parteDecimal);
        } else {
            this.valor = BigDecimal.ZERO;
            this.disponibilidade = Disponibilidade.INDISPONIVEL;
        }
    }


}
