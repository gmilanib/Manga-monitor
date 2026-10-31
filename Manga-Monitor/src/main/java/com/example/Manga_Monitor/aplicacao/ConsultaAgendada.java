package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;

import java.util.List;
import java.util.TimerTask;

public class ConsultaAgendada extends TimerTask {
    private VolumeRepository volumeRepository;
    private TentativaConsultaRepository tentativaConsultaRepository;

    public ConsultaAgendada(VolumeRepository volumeRepository, TentativaConsultaRepository tentativaConsultaRepository) {
    this.volumeRepository = volumeRepository;
    this.tentativaConsultaRepository = tentativaConsultaRepository;
    }

    @Override
    public void run() {
        List<Volume> volumes = volumeRepository.findAll();
        for (Volume volume : volumes) {
            System.out.println(volume.getTitulo());
            ConsultarVolume consultarVolume = new ConsultarVolume(volume, tentativaConsultaRepository);

        }
    }
}