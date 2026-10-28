package com.example.Manga_Monitor;

import com.example.Manga_Monitor.aplicacao.*;
import com.example.Manga_Monitor.apresentacao.ConsultarUmVolumeConsole;
import com.example.Manga_Monitor.apresentacao.ExclusaoVolumeConsole;
import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.TentativaConsultaRepository;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;

@SpringBootApplication
public class MangaMonitorApplication {

    public static void main(String[] args) {
        var contexto = SpringApplication.run(MangaMonitorApplication.class, args);
        VolumeRepository volumeRepository = contexto.getBean(VolumeRepository.class);
        TentativaConsultaRepository tentativaConsultaRepository = contexto.getBean(TentativaConsultaRepository.class);
        Historico historico = new Historico(tentativaConsultaRepository, volumeRepository);

        Scanner scanner = new Scanner(System.in);
        System.out.println("########## Escolha uma das opções abaixo ##########");
        System.out.print("1. Cadastrar novo volume; \n2.Consultar novo volume;\n3.Consulta agendada:\n");
        System.out.println("4. Excluir volume por título e número;");
        System.out.println("5. Consultar histórico de preços:");
        System.out.println("6. Consultar 1 volume:");
        System.out.println("##############################");
        if (!scanner.hasNextLine()) return;
        int opt;
        try {
            opt = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return;
        }
        switch (opt) {
            case 1:
                CadastrarVolume cadastrarVolume = new CadastrarVolume(volumeRepository, scanner);
                cadastrarVolume.cadastrarNovoVolume();
                break;
            case 2:
                List<Volume> volumes = volumeRepository.findAll();
                for (Volume volume : volumes) {
                    System.out.println(volume.getTitulo());
                    ConsultarVolume consultarVolume = new ConsultarVolume(volume, tentativaConsultaRepository);
                }
                historico.obterVolumes();
                historico.obterTentativas();
                historico.menorPreco();
                break;
            //Consulta continua a cada X horas;
            case 3:
                Timer timer = new Timer();
                ConsultaAgendada consultaAgendada = new ConsultaAgendada(volumeRepository, tentativaConsultaRepository);
                timer.schedule(consultaAgendada, 0, 120000);
                break;
            case 4:
                new ExclusaoVolumeConsole(contexto.getBean(ExcluirVolume.class), scanner, System.out).executar();
                break;
            case 5:
                historico.obterVolumes();
                historico.obterTentativas();
                historico.menorPreco();
                break;
            case 6:
               List<Volume> listaDeVolumes = new ConsultarUmVolumeConsole(contexto.getBean(ConsultarUmVolume.class), scanner, System.out).run();
               for (Volume volume : listaDeVolumes) {
                  new ConsultarVolume(volume, tentativaConsultaRepository);
               }
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
}

