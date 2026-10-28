package com.example.Manga_Monitor.infraestrutura.navegador;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.math.BigDecimal;

public final class ExtratorPrecoAmazon {
    private static final String PRECO = "#corePriceDisplay_desktop_feature_div .priceToPay";

    private ExtratorPrecoAmazon() {
    }

    public static BigDecimal extrair(Page pagina) {
        boolean temAcordeao = pagina.locator("#buyBoxAccordion").count() > 0;
        Locator precos = pagina.locator(temAcordeao
                ? "#buyBoxAccordion .a-accordion-active " + PRECO
                : PRECO);
        int quantidade = precos.count();
        if (quantidade == 0 && !temAcordeao) {
            return null;
        }
        if (quantidade != 1) {
            throw new IllegalStateException("Esperado um preço da oferta principal selecionada; encontrados: "
                    + quantidade + ".");
        }
        if (!precos.isVisible()) {
            return null;
        }

        String inteira = lerParte(precos, ".a-price-whole").replaceAll("\\s", "");
        String fracao = lerParte(precos, ".a-price-fraction");
        if (!inteira.matches("(?:[0-9]+|[0-9]{1,3}(?:\\.[0-9]{3})+),?")
                || !fracao.matches("[0-9]{2}")) {
            throw new IllegalStateException("Preço da oferta principal possui reais ou centavos inválidos.");
        }
        return new BigDecimal(inteira.replace(".", "").replace(",", "") + "." + fracao);
    }

    private static String lerParte(Locator preco, String seletor) {
        Locator parte = preco.locator(seletor);
        if (parte.count() != 1) {
            throw new IllegalStateException("Preço da oferta principal deve conter uma parte " + seletor + ".");
        }
        return parte.innerText().replace('\u00a0', ' ').trim();
    }
}
