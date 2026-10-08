package com.example.Manga_Monitor;

import com.example.Manga_Monitor.aplicacao.CadastrarVolume;
import com.example.Manga_Monitor.aplicacao.ConsultarVolume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
                TentativaConsultaRepository tentativaRepository = contexto.getBean(TentativaConsultaRepository.class);
                ConsultarVolume consultaVolume = new ConsultarVolume(volumeRepository ,tentativaRepository );
                consultaVolume.consultarVolume();
                break;

        }
        /*
         * TODO para montar o fluxo de consulta quando as classes estiverem prontas:
         * 1. criar VolumeJsonRepository usando filePath;
         * 2. criar ConsultorPaginaHttp;
         * 3. entregar ambos ao ConsultarPrimeiroVolume;
         * 4. entregar o caso de uso ao ConsultaVolumeConsole;
         * 5. iniciar uma única consulta;
         * 6. depois retirar a criação do repository de CadastroVolumeConsole, pois
         *    a classe principal deve ser o ponto de montagem das dependências.
         */

    }

}
