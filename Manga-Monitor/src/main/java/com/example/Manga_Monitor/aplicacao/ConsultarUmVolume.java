package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarUmVolume {
    private final VolumeRepository volumes;
    private final TentativaConsultaRepository tentativas;

    public ConsultarUmVolume(VolumeRepository volumes, TentativaConsultaRepository tentativas) {
        this.volumes = volumes;
        this.tentativas = tentativas;
    }
@Transactional
    public List<Volume> buscar(String titulo, int numero) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Informe ao menos parte do título.");
        }
        if (numero <= 0) {
            throw new IllegalArgumentException("O número do volume deve ser maior que zero.");
        }
        return volumes.buscarPorTituloEVolume(titulo.trim(), numero);
    }

}
