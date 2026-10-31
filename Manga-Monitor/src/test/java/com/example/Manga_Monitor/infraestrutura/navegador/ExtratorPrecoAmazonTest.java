package com.example.Manga_Monitor.infraestrutura.navegador;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class ExtratorPrecoAmazonTest {
    private static Playwright playwright;
    private static Browser navegador;
    private BrowserContext contexto;
    private Page pagina;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        navegador = playwright.chromium().launch();
    }

    @AfterAll
    static void fecharNavegador() {
        if (navegador != null) navegador.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    void abrirPaginaLocal() {
        contexto = navegador.newContext();
        pagina = contexto.newPage();
    }

    @AfterEach
    void fecharContexto() {
        contexto.close();
    }

    private static String preco(String reais, String centavos) {
        return "<div id='corePriceDisplay_desktop_feature_div'><span class='priceToPay'>"
                + "<span class='a-price-whole'>" + reais + "</span>"
                + "<span class='a-price-fraction'>" + centavos + "</span></span></div>";
    }

    @Test
    void deveEscolherOfertaAtivaComCentavosMesmoQuandoAlternativaVemAntes() {
        // Esperado: 43.44 da oferta ativa, ignorando a alternativa de entrega rápida.
        pagina.setContent("<div id='buyBoxAccordion'><div>Receba mais rápido!"
                + preco("48,", "35") + "</div><div class='a-accordion-active'>Compra única, Pix ou NuPay"
                + preco("43,", "44") + "</div></div>");
        assertThat(pagina.locator("#corePriceDisplay_desktop_feature_div .priceToPay").count()).isEqualTo(2);
        assertThatThrownBy(() -> pagina.locator("#corePriceDisplay_desktop_feature_div .priceToPay").isVisible())
                .hasMessageContaining("strict mode violation");
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isEqualByComparingTo("43.44");
    }

    @Test
    void deveSeguirOfertaAtivaMesmoQuandoAlternativaCustaMenos() {
        // Esperado: seleção pelo bloco ativo, nunca pelo menor preço ou pela ordem.
        pagina.setContent("<div id='buyBoxAccordion'><div>" + preco("30,", "00")
                + "</div><div class='a-accordion-active'>" + preco("48,", "35") + "</div></div>");
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isEqualByComparingTo("48.35");
    }

    @Test
    void deveLerPrecoUnicoSemAcordeaoComMilharesEVirgula() {
        // Esperado: BigDecimal 1234.05; manter centavo inicial zero e remover milhares.
        pagina.setContent(preco("1.234,<br>", "05"));
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isEqualByComparingTo("1234.05");
    }

    @Test
    void deveRejeitarDoisPrecosSemOfertaSelecionada() {
        // Esperado: falha explícita, sem escolher arbitrariamente a primeira ocorrência.
        pagina.setContent(preco("43,", "44") + preco("48,", "35"));
        assertThatThrownBy(() -> ExtratorPrecoAmazon.extrair(pagina))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("encontrados: 2");
    }

    @Test
    void deveRetornarAusenteQuandoAcordeaoNaoTemBlocoAtivo() {
        // Esperado: null (INDISPONIVEL no chamador), sem usar a oferta alternativa.
        pagina.setContent("<div id='buyBoxAccordion'>" + preco("48,", "35") + "</div>");
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isNull();
    }

    @Test
    void deveRejeitarCentavosAusentes() {
        // Esperado: falha, sem gravar 43.00 quando os centavos não foram obtidos.
        pagina.setContent(preco("43,", "44"));
        pagina.locator(".a-price-fraction").evaluate("e => e.remove()");
        assertThatThrownBy(() -> ExtratorPrecoAmazon.extrair(pagina))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining(".a-price-fraction");
    }

    @Test
    void deveRejeitarCentavosInvalidos() {
        // Esperado: rejeitar centavos com tamanho errado, sem arredondar ou completar.
        pagina.setContent(preco("43,", "4"));
        assertThatThrownBy(() -> ExtratorPrecoAmazon.extrair(pagina))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("inválidos");
    }

    @Test
    void deveRejeitarSeparadorDeMilharesInvalido() {
        // Esperado: rejeitar 1.23, em vez de produzir incorretamente 123.44.
        pagina.setContent(preco("1.23,", "44"));
        assertThatThrownBy(() -> ExtratorPrecoAmazon.extrair(pagina))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("inválidos");
    }

    @Test
    void deveRetornarAusenteQuandoSoHaOutrasOpcoesDeCompra() {
        pagina.setContent("<div id='buyBoxAccordion'><span>A partir de R$ 249,90</span>"
                + "<a>Ver todas as opções de compra</a></div>");
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isNull();
    }

    @Test
    void deveRetornarAusenteQuandoTemporariamenteIndisponivel() {
        pagina.setContent("<div id='buyBoxAccordion'>"
                + "<span>Temporariamente indisponível.</span></div>");
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isNull();
    }

    @Test
    void deveRetornarAusenteSemOfertaPrincipalMesmoComAvisoOculto() {
        // Esperado: null; a ausência da oferta principal não depende do aviso.
        pagina.setContent("<div id='buyBoxAccordion'>" + preco("48,", "35")
                + "<span hidden>Temporariamente indisponível.</span></div>");
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isNull();
    }

    @Test
    void deveRetornarAusenteQuandoBlocoAtivoNaoContemPrecoPrincipal() {
        // Esperado: null, mesmo havendo preço em outra oferta do acordeão.
        pagina.setContent("<div id='buyBoxAccordion'><div>" + preco("48,", "35")
                + "</div><div class='a-accordion-active'>Sem oferta principal</div></div>");
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isNull();
    }

    @Test
    void deveRetornarAusenteQuandoNaoHaPreco() {
        // Esperado: extrator não inventa preço em página sem oferta.
        pagina.setContent("<p>Produto indisponível</p>");
        assertThat(ExtratorPrecoAmazon.extrair(pagina)).isNull();
    }
}
