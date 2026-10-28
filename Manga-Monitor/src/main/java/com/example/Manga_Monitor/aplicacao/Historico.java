package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
            tentativaPorVolume.clear();

            for (TentativaConsulta tentativa : tentativas) {
                if (volume.getId() == tentativa.getVolume().getId()) {
                    tentativaPorVolume.add(tentativa);
                }
            }
            tentativaPorVolume.removeIf(tentativa -> tentativa.getValor().compareTo(new BigDecimal("1.00")) < 0);

            TentativaConsulta ultimaTentativa = tentativaPorVolume.getLast();

            tentativaPorVolume.sort(Comparator.comparing(TentativaConsulta::getValor));

            TentativaConsulta tentativaMenorPreco = tentativaPorVolume.getFirst();

            if (Objects.equals(ultimaTentativa.getValor(), tentativaMenorPreco.getValor())) {
                System.out.println(">>> Bom preço para compra <<<");
                System.out.println(ultimaTentativa.toString());
                System.out.println(volumes.get(0).getURL());
                System.out.println("---------------------");

            }else{
                System.out.println("---------------------");
            }

        }

    }
}
