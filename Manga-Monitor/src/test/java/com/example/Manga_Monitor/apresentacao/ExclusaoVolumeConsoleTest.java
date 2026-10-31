package com.example.Manga_Monitor.apresentacao;

import com.example.Manga_Monitor.aplicacao.ExcluirVolume;
import com.example.Manga_Monitor.dominio.Volume;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ExclusaoVolumeConsoleTest {
    private final ExcluirVolume servico = mock(ExcluirVolume.class);

    private String executar(String texto) {
        var bytes = new ByteArrayOutputStream();
        new ExclusaoVolumeConsole(servico, new Scanner(texto), new PrintStream(bytes)).executar();
        return bytes.toString(java.nio.charset.StandardCharsets.UTF_8);
    }

    private void prepararResultados() {
        Volume primeiro = new Volume("Naruto", 3, "https://loja/primeiro");
        Volume segundo = new Volume("Naruto", 3, "https://loja/segundo");
        ReflectionTestUtils.setField(primeiro, "id", 10L);
        ReflectionTestUtils.setField(segundo, "id", 20L);
        when(servico.buscar("Naru", 3)).thenReturn(List.of(primeiro, segundo));
    }

    @Test
    void deveExcluirSomenteCadastroSelecionadoAposConfirmacao() {
        prepararResultados();
        when(servico.excluir(20L)).thenReturn(true);
        String saida = executar("Naru\n3\n2\nSIM\n");
        assertThat(saida).contains("https://loja/primeiro", "https://loja/segundo",
                "Excluir Naruto, volume 3", "excluídos com sucesso");
        verify(servico).excluir(20L);
        verify(servico, never()).excluir(10L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0\n", "9\n", "-1\n", "abc\n", "1\nNÃO\n", "1\n\n", "1\n", ""})
    void cancelamentoSelecaoInvalidaOuFimDaEntradaNaoDevemExcluir(String restante) {
        prepararResultados();
        executar("Naru\n3\n" + restante);
        verify(servico, never()).excluir(anyLong());
    }

    @ParameterizedTest
    @ValueSource(strings = {"\n", "   \n", "Naru\n0\n", "Naru\n-1\n", "Naru\nabc\n", "Naru\n2147483648\n", ""})
    void entradaInvalidaNaoDeveAcessarPersistencia(String entrada) {
        executar(entrada);
        verifyNoInteractions(servico);
    }

    @Test
    void buscaSemResultadosDeveInformarUsuarioSemExcluir() {
        when(servico.buscar("Naru", 3)).thenReturn(List.of());
        assertThat(executar("Naru\n3\n")).contains("Nenhum volume encontrado");
        verify(servico, never()).excluir(anyLong());
    }

    @Test
    void cadastroRemovidoAposBuscaDeveSerInformado() {
        prepararResultados();
        assertThat(executar("Naru\n3\n1\nSIM\n")).contains("não existe mais");
    }

    @Test
    void falhaNaExclusaoDeveSerInformadaSemMensagemDeSucesso() {
        prepararResultados();
        when(servico.excluir(10L)).thenThrow(new IllegalStateException("Falha simulada"));
        assertThat(executar("Naru\n3\n1\nSIM\n"))
                .contains("Não foi possível concluir").doesNotContain("excluídos com sucesso");
    }
}
