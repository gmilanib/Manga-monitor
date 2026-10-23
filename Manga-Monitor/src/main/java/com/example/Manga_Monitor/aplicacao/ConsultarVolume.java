package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;

public class ConsultarVolume {
    private TentativaConsulta tentativaConsulta;
    private final TentativaConsultaRepository tentativaConsultaRepository;

    public ConsultarVolume(Volume volume, TentativaConsultaRepository tentativaConsultaRepository) {
        this.tentativaConsulta = new  TentativaConsulta(volume);
        this.tentativaConsultaRepository = tentativaConsultaRepository;

        tentativaConsultaRepository.save(tentativaConsulta);

    }



}
