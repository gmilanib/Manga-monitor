package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExcluirVolume {
    private final VolumeRepository volumes;
    private final TentativaConsultaRepository tentativas;

    public ExcluirVolume(VolumeRepository volumes, TentativaConsultaRepository tentativas) {
        this.volumes = volumes;
        this.tentativas = tentativas;
    }

    @Transactional(readOnly = true)
    public List<Volume> buscar(String titulo, int numero) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Informe ao menos parte do título.");
        }
        if (numero <= 0) {
            throw new IllegalArgumentException("O número do volume deve ser maior que zero.");
        }
        return volumes.buscarPorTituloEVolume(titulo.trim(), numero);
    }

    @Transactional
    public boolean excluir(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Selecione um cadastro válido.");
        }
        var volume = volumes.findById(id);
        if (volume.isEmpty()) return false;
        tentativas.deleteByVolumeId(id);
        tentativas.flush();
        volumes.delete(volume.get());
        volumes.flush();
        return true;
    }
}
