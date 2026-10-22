package com.example.Manga_Monitor;

import com.example.Manga_Monitor.aplicacao.CadastrarVolume;
import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MangaMonitorApplication {
    /**
     * 1. iniciar o contexto do Spring;
     * 2. definir o caminho Dados/volumes.json;
     * 3. criar a implementação VolumeJsonRepository;
     * 4. entregar o repository ao caso de uso CadastrarVolume;
     * 5. entregar o caso de uso à apresentação CadastroVolumeConsole;
     * 6. mandar a apresentação iniciar a interação com o usuário.
     * <p>
     * Esta classe apenas monta e inicia o fluxo. Ela não deve ler dados do
     * terminal, validar um volume ou manipular diretamente o arquivo JSON.
     */

    public static void main(String[] args) {
        var contexto = SpringApplication.run(MangaMonitorApplication.class, args);
        VolumeRepository volumeRepository = contexto.getBean(VolumeRepository.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("########## Escolha uma das opções abaixo ##########");
        System.out.print("1. Cadastrar novo volume; \n2.Consultar novo volume:\n3.Exibir volume de um novo volume");
        int opt = 2;
        switch (opt) {
            case 1:
                CadastrarVolume cadastrarVolume = new CadastrarVolume(volumeRepository);
                cadastrarVolume.cadastrarNovoVolume();
                break;
            case 2:
                List<Volume> volumes = volumeRepository.findAll();
                for (Volume volume : volumes){
                    System.out.println(volume.getTitulo());
                    TentativaConsulta tentativaConsulta = new TentativaConsulta(volume);
                    tentativaConsulta.tentativaConsulta(volume);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + opt);
        }
    }
}

