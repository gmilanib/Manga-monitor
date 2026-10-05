package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.consulta.TentativaConsultaRepository;
import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.infraestrutura.http.ConsultorPaginaHttp;
import com.example.Manga_Monitor.repository.VolumeJsonRepository;

import java.nio.file.Path;
import java.util.List;

/**
 * Coordena a primeira consulta real de um único volume cadastrado.
 */
public class ConsultarVolume {
    private Path filePath;
    private VolumeJsonRepository volumeJsonRepository;
    private ConsultorPaginaHttp consultorPaginaHttp;
    private TentativaConsultaRepository tentativaConsultaRepo;

    public ConsultarVolume(Path filePath) {
        this.filePath = filePath;
        VolumeJsonRepository volumeJsonRepository = new VolumeJsonRepository(filePath);
        this.volumeJsonRepository = volumeJsonRepository;
    }

    public TentativaConsulta consultarVolume(Path path) {
        VolumeJsonRepository volumeJsonRepository = new VolumeJsonRepository(filePath);
        List<Volume> volumes = volumeJsonRepository.buscarTodos();
        for (Volume volume : volumes) {
            ConsultorPaginaHttp consultorPaginaHttp = new ConsultorPaginaHttp();
            ResultadoPagina resultadoPagina = consultorPaginaHttp.consultar(volume);

            TentativaConsulta tentativaConsulta = new TentativaConsulta(volume, resultadoPagina.getPreco(), resultadoPagina.getMotivoErro());

            tentativaConsultaRepo.save(tentativaConsulta);
        }
        return null;
    }

}
