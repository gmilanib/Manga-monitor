package com.example.Manga_Monitor.repository;

import com.example.Manga_Monitor.dominio.Volume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VolumeRepository extends JpaRepository<Volume, Long> {
    List<Volume> findAllById(Long id);

}
