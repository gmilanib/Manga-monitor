package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.consulta.*;
import com.example.Manga_Monitor.dominio.*;
import com.example.Manga_Monitor.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.data.domain.*;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultarVolumeTest {
    private final VolumeRepository volumes = mock(VolumeRepository.class);
    private final TentativaConsultaRepository tentativas = mock(TentativaConsultaRepository.class);
    private final ConsultorPaginaVolume consultor = mock(ConsultorPaginaVolume.class);
    private final ConsultarVolume caso = new ConsultarVolume(volumes, tentativas, consultor);
    private final Volume volume = new Volume("Kaiju", 9, "https://www.amazon.com.br/dp/652590708X");

    @BeforeEach
    void preparar() {
        when(volumes.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(volume)));
        when(tentativas.save(any())).thenAnswer(chamada -> chamada.getArgument(0));
    }

    @Test
    @DisplayName("Consulta válida salva preço, condição Pix/NuPay e instante do volume persistido")
    void salvaSucesso() {
        when(consultor.consultar(volume)).thenReturn(new ResultadoPagina(Disponibilidade.DISPONIVEL, new BigDecimal("41.70")));
        var tentativa = caso.consultarVolume().getFirst();
        assertTrue(tentativa.isSucesso());
        assertSame(volume, tentativa.getVolume());
        assertEquals(new BigDecimal("41.70"), tentativa.getPreco());
        assertEquals(CondicaoPagamento.PIX_NUPAY, tentativa.getCondicaoPagamento());
        assertNotNull(tentativa.getInstante());
        verify(tentativas).save(tentativa);
        verify(volumes).findAll(PageRequest.of(0, 20, Sort.by("id")));
    }

    @Test
    @DisplayName("Falha do primeiro produto é salva e não impede consultar o próximo")
    void continuaAposFalhaTecnica() {
        var segundo = new Volume("Outro", 1, "https://www.amazon.com.br/dp/8545706820");
        when(volumes.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(volume, segundo)));
        when(consultor.consultar(volume)).thenThrow(new FalhaConsultaException("HTTP 503"));
        when(consultor.consultar(segundo)).thenReturn(new ResultadoPagina(Disponibilidade.DISPONIVEL, null));
        var resultados = caso.consultarVolume();
        assertFalse(resultados.getFirst().isSucesso());
        assertEquals("HTTP 503", resultados.getFirst().getMotivoErro());
        assertEquals(Disponibilidade.DESCONHECIDA, resultados.getFirst().getDisponibilidade());
        assertNull(resultados.getFirst().getPreco());
        assertTrue(resultados.get(1).isSucesso());
        assertNull(resultados.get(1).getPreco());
        verify(tentativas, times(2)).save(any());
    }

    @Test
    @DisplayName("Banco vazio não abre consultas e não grava tentativas")
    void listaVazia() {
        when(volumes.findAll(any(Pageable.class))).thenReturn(Page.empty());
        assertTrue(caso.consultarVolume().isEmpty());
        verifyNoInteractions(consultor, tentativas);
    }

    @Test
    @DisplayName("Falha no banco é propagada, sem mascará-la como falha da Amazon")
    void falhaDePersistencia() {
        when(consultor.consultar(volume)).thenReturn(new ResultadoPagina(Disponibilidade.DISPONIVEL, null));
        when(tentativas.save(any())).thenThrow(new IllegalStateException("Banco indisponível"));
        assertThrows(IllegalStateException.class, caso::consultarVolume);
    }
}
