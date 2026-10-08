package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.infraestrutura.http.ConsultorPaginaHttp;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;

import java.util.List;

public class ConsultarVolume {
    private VolumeRepository volumeRepository;
    private TentativaConsultaRepository tentativaConsultaRepository;
    private ConsultorPaginaHttp consultorPaginaHttp;

    public ConsultarVolume(VolumeRepository volumeRepository, TentativaConsultaRepository tentativaConsultaRepository) {
        this.volumeRepository = volumeRepository;
        this.tentativaConsultaRepository = tentativaConsultaRepository;
    }

    public TentativaConsulta consultarVolume() {
        TentativaConsulta consulta = new TentativaConsulta();
        List<Volume> volumes = volumeRepository.findAll();
        for (Volume volume : volumes) {
            ConsultorPaginaHttp consultorPaginaHttp = new ConsultorPaginaHttp();
            ResultadoPagina resultadoPagina = consultorPaginaHttp.consultar(volume);

            TentativaConsulta tentativaConsulta = new TentativaConsulta(volume, resultadoPagina.getPreco(), resultadoPagina.getMotivoErro());

            tentativaConsultaRepository.save(tentativaConsulta);

        }
        return null;
    }

}
