package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.consulta.ConsultorPaginaVolume;
import com.example.Manga_Monitor.consulta.FalhaConsultaException;
import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;

public class ConsultarVolume {
    private final VolumeRepository volumes;
    private final TentativaConsultaRepository tentativas;
    private final ConsultorPaginaVolume consultor;

    public ConsultarVolume(VolumeRepository volumes, TentativaConsultaRepository tentativas,
                           ConsultorPaginaVolume consultor) {
        this.volumes = volumes;
        this.tentativas = tentativas;
        this.consultor = consultor;
    }

    /** Uma rodada manual, sequencial, limitada a 20 cadastros. */
    public List<TentativaConsulta> consultarVolume() {
        List<TentativaConsulta> resultados = new ArrayList<>();
        for (Volume volume : volumes.findAll(PageRequest.of(0, 20, Sort.by("id")))) {
            TentativaConsulta tentativa;
            try {
                tentativa = new TentativaConsulta(volume, consultor.consultar(volume));
            } catch (FalhaConsultaException erro) {
                tentativa = TentativaConsulta.falha(volume, erro.getMessage());
            }
            // Falha de persistência interrompe a rodada; não é erro da loja.
            resultados.add(tentativas.save(tentativa));
        }
        return List.copyOf(resultados);
    }
}
