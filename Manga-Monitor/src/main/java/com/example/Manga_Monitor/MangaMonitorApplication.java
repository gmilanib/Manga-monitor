package com.example.Manga_Monitor;

import com.example.Manga_Monitor.aplicacao.CadastrarVolume;
import com.example.Manga_Monitor.aplicacao.ConsultarVolume;
import com.example.Manga_Monitor.infraestrutura.http.ConsultorPaginaHttp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
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
        SpringApplication.run(MangaMonitorApplication.class, args);

        Path filePath = Path.of(System.getProperty("user.dir"), "Dados", "volumes.json");


        Scanner scanner = new Scanner(System.in);
        int opt = scanner.nextInt();
        switch (opt) {
            case 1:
                CadastrarVolume cadastrarVolume = new CadastrarVolume(filePath);
                cadastrarVolume.cadastrarNovoVolume();
                break;
            case 2:
                ConsultarVolume consultaVolume = new ConsultarVolume(filePath);
                consultaVolume.consultarVolume(filePath);
                break;
            case 3:
                ConsultorPaginaHttp consultorPaginaHttp = new ConsultorPaginaHttp();
                consultorPaginaHttp.consulta();

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
