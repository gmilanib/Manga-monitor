package com.example.Manga_Monitor.dominio;

import com.example.Manga_Monitor.infraestrutura.navegador.NavegadorPlaywright;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class IdentificacaoTentativaTest {
    @Autowired VolumeRepository volumes;
    @Autowired TentativaConsultaRepository tentativas;
    @Autowired JdbcTemplate jdbc;

    @BeforeEach
    void limparBancoDeTeste() {
        jdbc.update("DELETE FROM tentativa_consulta");
        jdbc.update("DELETE FROM volume");
    }

    private TentativaConsulta consultarSemNavegador(Volume volume) {
        Page pagina = mock(Page.class);
        Locator elemento = mock(Locator.class);
        when(pagina.getByRole(any(), any())).thenReturn(elemento);
        when(pagina.locator(anyString())).thenReturn(elemento);
        when(elemento.isVisible()).thenReturn(false);
        try (var navegador = mockConstruction(NavegadorPlaywright.class,
                (mock, context) -> when(mock.getPage()).thenReturn(pagina))) {
            return new TentativaConsulta(volume);
        }
    }

    @Test
    void devePersistirIndisponivelQuandoAcordeaoNaoTemPrecoPrincipal() {
        // Esperado: salvar e reler INDISPONIVEL e zero, sem exigir aviso textual.
        Volume volume = volumes.saveAndFlush(new Volume("Gachiakuta", 10, "https://loja/10"));
        Page pagina = mock(Page.class);
        Locator continuar = mock(Locator.class);
        Locator acordeao = mock(Locator.class);
        Locator preco = mock(Locator.class);
        when(pagina.getByRole(any(), any())).thenReturn(continuar);
        when(pagina.locator("#buyBoxAccordion")).thenReturn(acordeao);
        when(acordeao.count()).thenReturn(1);
        when(pagina.locator("#buyBoxAccordion .a-accordion-active #corePriceDisplay_desktop_feature_div .priceToPay"))
                .thenReturn(preco);
        when(preco.count()).thenReturn(0);
        try (var navegador = mockConstruction(NavegadorPlaywright.class,
                (mock, context) -> when(mock.getPage()).thenReturn(pagina))) {
            tentativas.saveAndFlush(new TentativaConsulta(volume));
        }
        var relida = tentativas.findAll().getFirst();
        assertThat(relida.getDisponibilidade()).isEqualTo(Disponibilidade.INDISPONIVEL);
        assertThat(relida.getValor()).isEqualByComparingTo("0");
    }

    @Test
    void devePersistirERelerPrecoDaOfertaSelecionadaComCentavos() {
        // Esperado: valor 43.44 tanto no objeto criado quanto no registro relido pelo JPA.
        Volume volume = volumes.saveAndFlush(new Volume("Chainsaw man", 23, "https://loja/23"));
        Page pagina = mock(Page.class);
        Locator continuar = mock(Locator.class);
        Locator acordeao = mock(Locator.class);
        Locator preco = mock(Locator.class);
        Locator reais = mock(Locator.class);
        Locator centavos = mock(Locator.class);
        when(pagina.getByRole(any(), any())).thenReturn(continuar);
        when(pagina.locator("#buyBoxAccordion")).thenReturn(acordeao);
        when(acordeao.count()).thenReturn(1);
        when(pagina.locator("#buyBoxAccordion .a-accordion-active #corePriceDisplay_desktop_feature_div .priceToPay"))
                .thenReturn(preco);
        when(preco.count()).thenReturn(1);
        when(preco.isVisible()).thenReturn(true);
        when(preco.locator(".a-price-whole")).thenReturn(reais);
        when(preco.locator(".a-price-fraction")).thenReturn(centavos);
        when(reais.count()).thenReturn(1);
        when(centavos.count()).thenReturn(1);
        when(reais.innerText()).thenReturn("43,");
        when(centavos.innerText()).thenReturn("44");
        try (var navegador = mockConstruction(NavegadorPlaywright.class,
                (mock, context) -> when(mock.getPage()).thenReturn(pagina))) {
            TentativaConsulta tentativa = new TentativaConsulta(volume);
            assertThat(tentativa.getValor()).isEqualByComparingTo("43.44");
            assertThat(tentativa.getDisponibilidade()).isEqualTo(Disponibilidade.DISPONIVEL);
            tentativas.saveAndFlush(tentativa);
        }
        assertThat(tentativas.findAll().getFirst().getValor()).isEqualByComparingTo("43.44");
    }

    @Test
    void deveGravarTituloENumeroNaPropriaTabelaERelerPeloJpa() {
        Volume volume = volumes.saveAndFlush(new Volume("Naruto — Edição Especial", 3, "https://loja/3"));
        tentativas.saveAndFlush(consultarSemNavegador(volume));
        var linha = jdbc.queryForMap("SELECT titulo, numero, volume_id FROM tentativa_consulta");
        assertThat(linha.get("titulo")).isEqualTo("Naruto — Edição Especial");
        assertThat(((Number) linha.get("numero")).intValue()).isEqualTo(3);
        assertThat(((Number) linha.get("volume_id")).longValue()).isEqualTo(volume.getId());
        var relida = tentativas.findAll().getFirst();
        assertThat(relida.getTitulo()).isEqualTo("Naruto — Edição Especial");
        assertThat(relida.getNumero()).isEqualTo(3);
    }

    @Test
    void deveCapturarDadosAoCriarTentativaAntesDaPersistencia() {
        Volume volume = volumes.saveAndFlush(new Volume("Naruto", 3, "https://loja/3"));
        TentativaConsulta tentativa = consultarSemNavegador(volume);
        ReflectionTestUtils.setField(volume, "titulo", "Nome novo");
        ReflectionTestUtils.setField(volume, "numero", 4);
        volumes.saveAndFlush(volume);
        tentativas.saveAndFlush(tentativa);
        var relida = tentativas.findAll().getFirst();
        assertThat(relida.getTitulo()).isEqualTo("Naruto");
        assertThat(relida.getNumero()).isEqualTo(3);
    }

    @Test
    void novasTentativasDevemCapturarCadastroAtualSemAlterarHistoricoAnterior() {
        Volume volume = volumes.saveAndFlush(new Volume("Naruto", 3, "https://loja/3"));
        tentativas.saveAndFlush(consultarSemNavegador(volume));
        jdbc.update("UPDATE volume SET titulo = ?, numero = ? WHERE id = ?", "Novo título", 4, volume.getId());
        Volume atualizado = volumes.findById(volume.getId()).orElseThrow();
        tentativas.saveAndFlush(consultarSemNavegador(atualizado));
        assertThat(jdbc.queryForList("SELECT titulo FROM tentativa_consulta ORDER BY id", String.class))
                .containsExactly("Naruto", "Novo título");
        assertThat(jdbc.queryForList("SELECT numero FROM tentativa_consulta ORDER BY id", Integer.class))
                .containsExactly(3, 4);
    }

    @Test
    void atualizacaoJpaNaoDeveRegravarIdentificacaoHistorica() {
        Volume volume = volumes.saveAndFlush(new Volume("Naruto", 3, "https://loja/3"));
        tentativas.saveAndFlush(consultarSemNavegador(volume));
        TentativaConsulta relida = tentativas.findAll().getFirst();
        ReflectionTestUtils.setField(relida, "titulo", "Alteração indevida");
        ReflectionTestUtils.setField(relida, "numero", 99);
        tentativas.saveAndFlush(relida);
        assertThat(jdbc.queryForObject("SELECT titulo FROM tentativa_consulta", String.class)).isEqualTo("Naruto");
        assertThat(jdbc.queryForObject("SELECT numero FROM tentativa_consulta", Integer.class)).isEqualTo(3);
    }

    @Test
    void volumeNuloDeveSerRejeitadoAntesDeAbrirNavegador() {
        try (var navegador = mockConstruction(NavegadorPlaywright.class)) {
            assertThatNullPointerException().isThrownBy(() -> new TentativaConsulta(null))
                    .withMessage("Volume não pode ser nulo.");
            assertThat(navegador.constructed()).isEmpty();
        }
    }
}
