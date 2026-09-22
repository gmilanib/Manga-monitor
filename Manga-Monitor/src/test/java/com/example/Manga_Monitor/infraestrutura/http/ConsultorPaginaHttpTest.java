package com.example.Manga_Monitor.infraestrutura.http;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Roteiro para testes locais da extração. Use HTML salvo como fixture e um
 * servidor HTTP falso; não acesse a Amazon durante a suíte automatizada.
 */
@Disabled("Habilite ao implementar o cliente HTTP e a extração")
class ConsultorPaginaHttpTest {

    @Test
    void deveExtrairProdutoDisponivelComPreco() {
        // HTML disponível com R$ 39,90 -> DISPONIVEL e BigDecimal("39.90").
    }

    @Test
    void deveExtrairProdutoIndisponivelSemInventarPreco() {
        // HTML indisponível -> INDISPONIVEL e preço null, nunca zero.
    }

    @Test
    void deveFalharQuandoRespostaExcedeTimeout() {
        // Servidor lento -> exceção clara cuja causa identifica o timeout.
    }

    @Test
    void deveFalharQuandoHtmlNaoTemInformacoesReconhecidas() {
        // HTML inesperado -> falha técnica, não INDISPONIVEL.
    }
}
