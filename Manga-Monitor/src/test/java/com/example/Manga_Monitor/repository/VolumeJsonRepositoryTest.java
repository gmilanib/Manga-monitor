package com.example.Manga_Monitor.repository;

import com.example.Manga_Monitor.dominio.Volume;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class VolumeJsonRepositoryTest {
    @TempDir
    Path diretorioTemporario;

    @Test
    void deveCriarArquivoAoSalvarPrimeiroVolume() {
        Path arquivo = diretorioTemporario.resolve("Dados").resolve("volumes.json");
        assertFalse(Files.exists(arquivo));

        Volume volume = new Volume("Fullmetal Alchemist", 19, "https://amazon.com.br/fma-19");

        VolumeRepository repository = new VolumeJsonRepository(arquivo);

        repository.salvar(volume);

        assertTrue(Files.exists(arquivo));
    }

    @Test
    void deveGravarPrimeiroVolumeNoArquivoJson() throws Exception {
        Path arquivo = diretorioTemporario
                .resolve("Dados")
                .resolve("volumes.json");

        Volume volume = new Volume(
                "Fullmetal Alchemist",
                19,
                "https://amazon.com.br/fma-19"
        );

        VolumeRepository repository = new VolumeJsonRepository(arquivo);

        repository.salvar(volume);

        String conteudo = Files.readString(arquivo);
        JsonNode raiz = new ObjectMapper().readTree(conteudo);

        assertTrue(raiz.isArray());
        assertEquals(1, raiz.size());

        JsonNode volumeJson = raiz.get(0);

        assertEquals(
                "Fullmetal Alchemist",
                volumeJson.get("Titulo").asText()
        );
        assertEquals(
                19,
                volumeJson.get("Volume").asInt()
        );
        assertEquals(
                "https://amazon.com.br/fma-19",
                volumeJson.get("URL").asText()
        );
    }


    @Test
    void deveAdicionarSegundoVolumeSemApagarOPrimeiro() throws Exception {
        Path arquivo = diretorioTemporario
                .resolve("Dados")
                .resolve("volumes.json");

        Volume primeiro = new Volume(
                "Fullmetal Alchemist",
                19,
                "https://amazon.com.br/fma-19"
        );

        Volume segundo = new Volume(
                "Berserk",
                1,
                "https://amazon.com.br/berserk-1"
        );

        VolumeRepository repository = new VolumeJsonRepository(arquivo);

        repository.salvar(primeiro);
        repository.salvar(segundo);

        JsonNode raiz = new ObjectMapper()
                .readTree(Files.readString(arquivo));

        assertEquals(2, raiz.size());
        assertEquals(
                "Fullmetal Alchemist",
                raiz.get(0).get("Titulo").asText()
        );
        assertEquals(
                "Berserk",
                raiz.get(1).get("Titulo").asText()
        );
    }
}