package com.example.Manga_Monitor.repository;

import com.example.Manga_Monitor.aplicacao.ConsultarVolume;
import com.example.Manga_Monitor.dominio.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PersistenciaConsultaTest {
    @Autowired VolumeRepository volumes;
    @Autowired TentativaConsultaRepository tentativas;
    @Autowired EntityManager entidades;

    @Test
    @DisplayName("SQLite relê preço e falha do mesmo volume, preservando o histórico e a condição")
    void persisteHistorico() {
        var volume = volumes.saveAndFlush(new Volume("Kaiju", 9, "https://www.amazon.com.br/dp/652590708X"));
        tentativas.save(new TentativaConsulta(volume, new ResultadoPagina(Disponibilidade.DISPONIVEL, new BigDecimal("41.70"))));
        tentativas.save(TentativaConsulta.falha(volume, "HTTP 503"));
        tentativas.flush();
        entidades.clear();
        var historico = tentativas.findAll();
        assertEquals(2, historico.size());
        assertEquals(1, volumes.count());
        var sucesso = historico.stream().filter(TentativaConsulta::isSucesso).findFirst().orElseThrow();
        var falha = historico.stream().filter(t -> !t.isSucesso()).findFirst().orElseThrow();
        assertEquals(0, new BigDecimal("41.70").compareTo(sucesso.getPreco()));
        assertEquals(CondicaoPagamento.PIX_NUPAY, sucesso.getCondicaoPagamento());
        assertNull(falha.getPreco());
        assertEquals("HTTP 503", falha.getMotivoErro());
        assertSame(sucesso.getVolume(), falha.getVolume());
    }

    @Test
    @DisplayName("Com 21 cadastros, uma rodada consulta e salva somente os primeiros 20")
    void respeitaLimiteDaRodada() {
        for (int i = 1; i <= 21; i++) {
            volumes.save(new Volume("Kaiju", i, "https://www.amazon.com.br/dp/652590708X"));
        }
        volumes.flush();
        AtomicInteger consultas = new AtomicInteger();
        var caso = new ConsultarVolume(volumes, tentativas, volume -> {
            consultas.incrementAndGet();
            return new ResultadoPagina(Disponibilidade.DISPONIVEL, new BigDecimal("41.70"));
        });
        var resultados = caso.consultarVolume();
        assertEquals(20, consultas.get());
        assertEquals(20, tentativas.count());
        assertEquals(1, resultados.getFirst().getVolume().getNumero());
        assertEquals(20, resultados.getLast().getVolume().getNumero());
    }
}
