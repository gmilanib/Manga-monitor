package com.example.Manga_Monitor.repository;

import com.example.Manga_Monitor.dominio.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VolumeRepository extends JpaRepository<Volume, Long> {
    @Query("select v from Volume v where locate(lower(:titulo), lower(v.titulo)) > 0 "
            + "and v.numero = :numero order by v.id")
    List<Volume> buscarPorTituloEVolume(@Param("titulo") String titulo, @Param("numero") int numero);
}
