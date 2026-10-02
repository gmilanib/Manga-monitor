package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.infraestrutura.http.ConsultorPaginaHttp;
import com.example.Manga_Monitor.repository.VolumeJsonRepository;

import java.nio.file.Path;
import java.util.List;

/**
 * Coordena a primeira consulta real de um único volume cadastrado.
 */
public class ConsultarVolume {
    private Path filePath;
    private VolumeJsonRepository volumeJsonRepository;
    private ConsultorPaginaHttp consultorPaginaHttp;

    public ConsultarVolume(Path filePath) {
        this.filePath = filePath;
        VolumeJsonRepository volumeJsonRepository = new VolumeJsonRepository(filePath);
        this.volumeJsonRepository = volumeJsonRepository;
    }
    /**
     * TODO ao implementar:
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
    public TentativaConsulta consultarVolume(Path path) {
        VolumeJsonRepository volumeJsonRepository = new VolumeJsonRepository(filePath);
        List<Volume> volumes = volumeJsonRepository.buscarTodos();
        for (Volume volume : volumes) {
            ConsultorPaginaHttp consultorPaginaHttp = new ConsultorPaginaHttp();
            ResultadoPagina resultadoPagina = consultorPaginaHttp.consultar(volume);

            TentativaConsulta tentativaConsulta = new TentativaConsulta(volume, resultadoPagina.getPreco(), resultadoPagina.getMotivoErro());
        }
    return null;
    }

}
