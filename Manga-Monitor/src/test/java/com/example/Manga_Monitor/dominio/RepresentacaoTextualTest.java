package com.example.Manga_Monitor.dominio;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RepresentacaoTextualTest {
    @Test
    void volumeDeveExibirUmCampoPorLinha() {
        Volume volume = new Volume("Naruto", 3, "https://loja/3");
        ReflectionTestUtils.setField(volume, "id", 7L);

        assertThat(volume.toString()).isEqualTo(
                "ID: 7\nTítulo: Naruto\nNúmero: 3\nURL: https://loja/3");
    }

    @Test
    void tentativaDeveExibirDadosHistoricosSemAcessarVolume() {
        TentativaConsulta tentativa = new TentativaConsulta();
        ReflectionTestUtils.setField(tentativa, "id", 12L);
        ReflectionTestUtils.setField(tentativa, "titulo", "Naruto");
        ReflectionTestUtils.setField(tentativa, "numero", 3);
        ReflectionTestUtils.setField(tentativa, "data", LocalDateTime.of(2026, 10, 27, 14, 30));
        ReflectionTestUtils.setField(tentativa, "disponibilidade", Disponibilidade.DISPONIVEL);
        ReflectionTestUtils.setField(tentativa, "valor", new BigDecimal("29.90"));

        assertThat(tentativa.toString()).isEqualTo(
                "ID: 12\nTítulo: Naruto\nNúmero: 3\nData: 2026-10-27T14:30"
                        + "\nDisponibilidade: DISPONIVEL\nValor: 29.90");
    }

    @Test
    void entidadesComCamposNulosDevemSerImpressasSemExcecao() {
        assertThat(new Volume().toString()).isEqualTo(
                "ID: 0\nTítulo: null\nNúmero: 0\nURL: null");
        assertThat(new TentativaConsulta().toString()).isEqualTo(
                "ID: 0\nTítulo: null\nNúmero: null\nData: null"
                        + "\nDisponibilidade: null\nValor: null");
    }
}
