package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CadastroComMenuTest {
    @Test
    void cadastroDeveLerDadosAposEscolhaDoMenuNoMesmoScanner() {
        var repository = mock(VolumeRepository.class);
        var entrada = new Scanner("1\nNaruto\n3\nhttps://loja/volume\n");
        assertThat(entrada.nextLine()).isEqualTo("1");
        new CadastrarVolume(repository, entrada).cadastrarNovoVolume();
        var cadastro = ArgumentCaptor.forClass(Volume.class);
        verify(repository).save(cadastro.capture());
        assertThat(cadastro.getValue().getTitulo()).isEqualTo("Naruto");
        assertThat(cadastro.getValue().getNumero()).isEqualTo(3);
        assertThat(cadastro.getValue().getURL()).isEqualTo("https://loja/volume");
    }
}
