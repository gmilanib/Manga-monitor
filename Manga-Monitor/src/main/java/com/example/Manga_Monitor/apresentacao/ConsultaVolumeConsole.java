package com.example.Manga_Monitor.apresentacao;

import com.example.Manga_Monitor.aplicacao.ConsultarVolume;
import com.example.Manga_Monitor.dominio.TentativaConsulta;
import java.text.NumberFormat;
import java.util.Locale;

public class ConsultaVolumeConsole {
    private final ConsultarVolume consulta;

    public ConsultaVolumeConsole(ConsultarVolume consulta) {
        this.consulta = consulta;
    }

    public void iniciar() {
        var tentativas = consulta.consultarVolume();
        if (tentativas.isEmpty()) {
            System.out.println("Nenhum volume cadastrado neste banco.");
        }
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        for (TentativaConsulta tentativa : tentativas) {
            System.out.printf("%s — volume %d — %s%n", tentativa.getVolume().getTitulo(),
                    tentativa.getVolume().getNumero(), tentativa.getInstante());
            if (!tentativa.isSucesso()) {
                System.out.println("Falha na consulta: " + tentativa.getMotivoErro());
            } else {
                System.out.println("Disponibilidade: " + tentativa.getDisponibilidade());
                System.out.println("Preço à vista (Pix/NuPay), sem frete: "
                        + (tentativa.getPreco() == null ? "não informado" : moeda.format(tentativa.getPreco())));
            }
        }
    }
}
