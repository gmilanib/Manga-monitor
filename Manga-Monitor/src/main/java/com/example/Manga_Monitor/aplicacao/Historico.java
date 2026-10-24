package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Historico {
    private List<TentativaConsulta> tentativas;
    private List<Volume> volumes;
    private TentativaConsultaRepository tentativaRepository;
    private VolumeRepository volumesRepository;
    private List<TentativaConsulta> tentativaPorVolume = new ArrayList<>();


    public Historico(TentativaConsultaRepository tentativaRepository, VolumeRepository volumesRepository) {
        this.tentativaRepository = tentativaRepository;
        this.volumesRepository = volumesRepository;
    }

    public void obterTentativas() {
        this.tentativas = tentativaRepository.findAll();
    }

    public void obterVolumes() {
        this.volumes = volumesRepository.findAll();
    }

    public void menorPreco() {
        for (Volume volume : volumes) {
            for (TentativaConsulta tentativa : tentativas) {
                if (volume.getId() == tentativa.getVolume().getId()) {
                    tentativaPorVolume.add(tentativa);
                }
            }
        }
    }
}
