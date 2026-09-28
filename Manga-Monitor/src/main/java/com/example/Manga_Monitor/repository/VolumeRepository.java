package com.example.Manga_Monitor.repository;

import com.example.Manga_Monitor.dominio.Volume;

import java.util.List;
import java.util.Optional;

public interface VolumeRepository {
     void salvar(Volume volume);

     /**
      * Recupera o primeiro volume cadastrado nesta etapa inicial do projeto.
      *
      * TODO ao implementar:
      * 1. devolver Optional.empty() quando não existir volume cadastrado;
      * 2. devolver o primeiro Volume quando houver dados;
      * 3. não realizar requisição HTTP nesta camada.
      */
     Optional<Volume> buscarPrimeiro();

     public List<Volume> buscarTodos();

}
