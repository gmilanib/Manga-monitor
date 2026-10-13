package com.example.Manga_Monitor;

import com.example.Manga_Monitor.aplicacao.CadastrarVolume;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;

@SpringBootApplication
public class MangaMonitorApplication {
    public static void main(String[] args) {
        boolean consultar = Arrays.asList(args).contains("--consultar");
        boolean cadastrar = Arrays.asList(args).contains("--cadastrar");
        if (consultar == cadastrar) {
            System.out.println("Informe --consultar para uma rodada ou --cadastrar para cadastrar um volume.");
            return;
        }
        try (var contexto = SpringApplication.run(MangaMonitorApplication.class, args)) {
            VolumeRepository volumes = contexto.getBean(VolumeRepository.class);
            if (cadastrar) {
                new CadastrarVolume(volumes).cadastrarNovoVolume();
                return;
            }
            /* EXERCÍCIO 6 — Integrar a consulta depois de implementar as classes.
             * 1. Leia sem-janela e timeout-ms do ambiente Spring; defina padrões.
             * 2. Crie ConsultorPaginaPlaywright em um escopo com fechamento garantido.
             * 3. Obtenha TentativaConsultaRepository do contexto Spring.
             * 4. Monte ConsultarVolume com volumes, tentativas e o consultor.
             * 5. Entregue o caso de uso a ConsultaVolumeConsole e inicie a rodada.
             * 6. Encerre o consultor antes de encerrar o contexto Spring.
             * Reintroduza os imports necessários conforme implementar os passos.
             * Enquanto este trecho for só comentário, --consultar não consulta
             * nem registra tentativas. O fluxo de cadastro permanece implementado.
             */
        }
    }
}
