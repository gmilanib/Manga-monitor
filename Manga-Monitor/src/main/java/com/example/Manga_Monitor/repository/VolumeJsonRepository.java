package com.example.Manga_Monitor.repository;

import com.example.Manga_Monitor.dominio.Volume;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VolumeJsonRepository implements VolumeRepository {

    private final Path arquivo;
    private ObjectMapper objectMapper = new ObjectMapper();

    public VolumeJsonRepository(Path arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void salvar(Volume volume) {
        try {
            Files.createDirectories(arquivo.getParent());

            List<Map<String, Object>> volumesJson = new ArrayList<>();

            if (Files.exists(arquivo)) {
                List<Map<String, Object>> volumesExistentes =
                        objectMapper.readValue(
                                arquivo.toFile(),
                                new TypeReference<List<Map<String, Object>>>() {
                                }
                        );

                volumesJson.addAll(volumesExistentes);
            }

            Map<String, Object> novoVolume = Map.of(
                    "titulo", volume.getTitulo(),
                    "numero", volume.getNumero(),
                    "URL", volume.getURL()
            );

            volumesJson.add(novoVolume);

            objectMapper.writeValue(arquivo.toFile(), volumesJson);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Não foi possível criar o arquivo de volumes.",
                    exception
            );
        }
    }

    /**
     * TODO para ler o primeiro cadastro de volumes.json:
     * 1. verificar se o arquivo existe; se não existir, retornar Optional.empty();
     * 2. ler o JSON como uma lista;
     * 3. se a lista estiver vazia, retornar Optional.empty();
     * 4. ler Titulo, Volume e URL do primeiro item;
     * 5. construir um Volume e devolvê-lo dentro de Optional;
     * 6. transformar IOException ou JSON inválido em IllegalStateException com
     *    uma mensagem clara e manter a exceção original como causa.
     */
    @Override
    public Optional<Volume> buscarPrimeiro() {
        throw new UnsupportedOperationException("Implemente a leitura do primeiro volume");
    }

    @Override
    public List<Volume> buscarTodos() {
        return objectMapper.readValue(arquivo, new TypeReference<List<Volume>>() {
        });
    }


}
