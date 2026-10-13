package com.example.Manga_Monitor.dominio;

import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TentativaConsultaTest {
    private final Volume volume = new Volume("Kaiju", 9, "https://www.amazon.com.br/dp/652590708X");

    @Test
    @DisplayName("Consulta sem estoque é sucesso com preço ausente")
    void indisponibilidadeNaoEhFalha() {
        var tentativa = new TentativaConsulta(volume, new ResultadoPagina(Disponibilidade.INDISPONIVEL, null));
        assertTrue(tentativa.isSucesso());
        assertNull(tentativa.getPreco());
        assertNull(tentativa.getMotivoErro());
        assertEquals(Disponibilidade.INDISPONIVEL, tentativa.getDisponibilidade());
    }

    @Test
    @DisplayName("Ausência de preço mantém estoque informado e nunca vira zero")
    void precoAusente() {
        var tentativa = new TentativaConsulta(volume, new ResultadoPagina(Disponibilidade.DISPONIVEL, null));
        assertEquals(Disponibilidade.DISPONIVEL, tentativa.getDisponibilidade());
        assertNull(tentativa.getPreco());
    }

    @Test
    @DisplayName("Preço zero, negativo ou associado a falta de estoque é rejeitado")
    void rejeitaPrecoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new ResultadoPagina(Disponibilidade.DISPONIVEL, BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> new ResultadoPagina(Disponibilidade.DISPONIVEL, BigDecimal.ONE.negate()));
        assertThrows(IllegalArgumentException.class, () -> new ResultadoPagina(Disponibilidade.INDISPONIVEL, BigDecimal.ONE));
    }

    @Test
    @DisplayName("Falha exige motivo e volume, sem preço ou estoque inventados")
    void validaFalha() {
        assertThrows(IllegalArgumentException.class, () -> TentativaConsulta.falha(volume, " "));
        assertThrows(NullPointerException.class, () -> TentativaConsulta.falha(null, "timeout"));
        var tentativa = TentativaConsulta.falha(volume, "timeout");
        assertFalse(tentativa.isSucesso());
        assertNull(tentativa.getPreco());
        assertEquals(Disponibilidade.DESCONHECIDA, tentativa.getDisponibilidade());
        assertEquals("timeout", tentativa.getMotivoErro());
    }
}
