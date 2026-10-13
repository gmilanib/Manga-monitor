package com.example.Manga_Monitor.repository;

import com.example.Manga_Monitor.dominio.Volume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolumeRepository extends JpaRepository<Volume, Long> {
}
