package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoricoTest {
    private final PrintStream consoleOriginal = System.out;
    private final ByteArrayOutputStream saida = new ByteArrayOutputStream();
    private final TentativaConsultaRepository tentativas = mock(TentativaConsultaRepository.class);
    private final VolumeRepository volumes = mock(VolumeRepository.class);

    @BeforeEach
    void capturarConsole() {
        System.setOut(new PrintStream(saida));
    }

    @AfterEach
    void restaurarConsole() {
        System.setOut(consoleOriginal);
    }

    private Volume volume(long id) {
        Volume volume = new Volume("Mangá " + id, (int) id, "https://loja/" + id);
        ReflectionTestUtils.setField(volume, "id", id);
        return volume;
    }

    private TentativaConsulta tentativa(Volume volume, String valor) {
        TentativaConsulta tentativa = mock(TentativaConsulta.class);
        when(tentativa.getVolume()).thenReturn(volume);
        when(tentativa.getValor()).thenReturn(new BigDecimal(valor));
        return tentativa;
    }

    private void calcular(List<Volume> cadastros, List<TentativaConsulta> consultas) {
        when(volumes.findAll()).thenReturn(cadastros);
        when(tentativas.findAll()).thenReturn(consultas);
        Historico historico = new Historico(tentativas, volumes);
        historico.obterVolumes();
        historico.obterTentativas();
        historico.menorPreco();
    }

    @Test
    void deveContinuarAposVolumeComTodasAsTentativasZeradas() {
        // Esperado: não lançar exceção; analisar também o próximo volume disponível.
        Volume semOferta = volume(1);
        Volume disponivel = volume(2);
        assertThatCode(() -> calcular(List.of(semOferta, disponivel),
                List.of(tentativa(semOferta, "0"), tentativa(semOferta, "0"),
                        tentativa(disponivel, "43.44")))).doesNotThrowAnyException();
        assertThat(saida.toString()).contains("Sem preço disponível no histórico", "https://loja/2");
    }

    @Test
    void deveTratarVolumeSemTentativas() {
        // Esperado: avisar que não há preço, sem acessar uma lista vazia.
        assertThatCode(() -> calcular(List.of(volume(1)), List.of())).doesNotThrowAnyException();
        assertThat(saida.toString()).contains("Sem preço disponível no histórico");
    }

    @Test
    void naoDeveRecomendarCompraQuandoUltimaConsultaEstaIndisponivel() {
        // Esperado: preço antigo não transforma a última consulta zerada em recomendação.
        Volume volume = volume(1);
        calcular(List.of(volume), List.of(tentativa(volume, "40.00"), tentativa(volume, "0")));
        assertThat(saida.toString()).doesNotContain("Bom preço para compra");
    }

    @Test
    void deveExibirUrlDoVolumeAnalisado() {
        // Esperado: recomendação do segundo volume usa sua URL, não a do primeiro cadastro.
        Volume primeiro = volume(1);
        Volume segundo = volume(2);
        calcular(List.of(primeiro, segundo), List.of(tentativa(primeiro, "30"),
                tentativa(primeiro, "40"), tentativa(segundo, "43.44")));
        assertThat(saida.toString()).contains("https://loja/2").doesNotContain("https://loja/1");
    }

    @Test
    void naoDeveRecomendarPrecoAcimaDoMenorHistorico() {
        // Esperado: consulta atual mais cara não recebe aviso de bom preço.
        Volume volume = volume(1);
        calcular(List.of(volume), List.of(tentativa(volume, "30"), tentativa(volume, "40")));
        assertThat(saida.toString()).doesNotContain("Bom preço para compra");
    }

    @Test
    void deveCompararPrecosPeloValorIndependentementeDaEscala() {
        // Esperado: 40 e 40.00 representam o mesmo menor preço.
        Volume volume = volume(1);
        calcular(List.of(volume), List.of(tentativa(volume, "40"), tentativa(volume, "40.00")));
        assertThat(saida.toString()).contains("Bom preço para compra");
    }
}
