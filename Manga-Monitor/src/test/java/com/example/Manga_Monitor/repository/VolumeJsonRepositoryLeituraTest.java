package com.example.Manga_Monitor.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Roteiro de testes para implementar junto com buscarPrimeiro().
 */
@Disabled("Habilite cada teste ao implementar a leitura do JSON")
class VolumeJsonRepositoryLeituraTest {

    @Test
    void deveRetornarPrimeiroVolumeDoJson() {
        // Dado um JSON com um volume, espere título, número e URL equivalentes.
    }

    @Test
    void deveRetornarVazioQuandoArquivoNaoExiste() {
        // Use @TempDir; não escreva arquivo; espere Optional.empty().
    }

    @Test
    void deveRetornarVazioQuandoListaEstaVazia() {
        // Grave [] em @TempDir e espere Optional.empty().
    }

    @Test
    void deveInformarFalhaQuandoJsonForInvalido() {
        // Grave JSON inválido e espere IllegalStateException com causa preservada.
    }
}
