package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.repository.VolumeJsonRepository;

import java.nio.file.Path;

/**
 * Coordena a primeira consulta real de um único volume cadastrado.
 */
public class ConsultarVolume {
    private Path filePath;
    private VolumeJsonRepository volumeJsonRepository;

    public ConsultarVolume(Path filePath) {
        this.filePath = filePath;
        VolumeJsonRepository volumeJsonRepository = new VolumeJsonRepository(filePath);
        this.volumeJsonRepository = volumeJsonRepository;
    }
    /**
     * TODO ao implementar:
     * 1. declarar VolumeRepository e ConsultorPaginaVolume como dependências;
     * 2. recebê-las pelo construtor;
     * 3. criar um método executar() que devolva TentativaConsulta;
     * 4. pedir ao repositório o primeiro volume;
     * 5. informar claramente quando nenhum volume estiver cadastrado;
     * 6. pedir ao consultor que consulte a página desse volume;
     * 7. em sucesso, criar TentativaConsulta com LocalDateTime atual, resultado
     *    da página, sucesso=true e motivoErro ausente;
     * 8. em falha técnica, criar TentativaConsulta com sucesso=false,
     *    disponibilidade DESCONHECIDA, preço ausente e motivo específico;
     * 9. não usar Path, ObjectMapper, HttpClient, HTML ou Scanner aqui.
     */
    public void consultarVolume(Path path) {

    }

}
