package com.example.Manga_Monitor.consulta;

import com.example.Manga_Monitor.dominio.TentativaConsulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TentativaConsultaRepository extends JpaRepository<TentativaConsulta, Long> {
    List<TentativaConsulta> findByVolumeId(Long volumeId);

    List<TentativaConsulta>
    findByVolumeIdOrderByInstanteDesc(Long volumeId);
}
