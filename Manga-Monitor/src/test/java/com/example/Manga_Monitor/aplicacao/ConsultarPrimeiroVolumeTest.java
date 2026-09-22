package com.example.Manga_Monitor.aplicacao;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Roteiro dos testes do caso de uso. Substitua repository e consultor por
 * objetos falsos para testar a coordenação sem arquivo nem internet.
 */
@Disabled("Habilite ao implementar o caso de uso")
class ConsultarPrimeiroVolumeTest {

    @Test
    void deveCriarTentativaBemSucedidaParaOfertaDisponivel() {
        // Resultado DISPONIVEL/39.90 -> sucesso=true, preço e instante presentes.
    }

    @Test
    void deveCriarTentativaBemSucedidaParaOfertaIndisponivel() {
        // Resultado INDISPONIVEL/null -> sucesso=true e preço ausente.
    }

    @Test
    void deveRegistrarFalhaTecnicaSemMarcarProdutoIndisponivel() {
        // Erro HTTP -> sucesso=false, DESCONHECIDA, preço null e motivo presente.
    }

    @Test
    void deveInformarQuandoNaoExisteVolumeCadastrado() {
        // Repository vazio -> erro claro e nenhuma chamada ao consultor.
    }
}
