package com.example.Manga_Monitor.apresentacao;

import com.example.Manga_Monitor.aplicacao.ConsultarUmVolume;
import com.example.Manga_Monitor.dominio.Volume;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class ConsultarUmVolumeConsole {
    private final ConsultarUmVolume consultarumVolume;
    private final Scanner entrada;
    private final PrintStream saida;

    public ConsultarUmVolumeConsole(ConsultarUmVolume consultarUmVolume, Scanner entrada, PrintStream saida) {
        this.consultarumVolume = consultarUmVolume;
        this.entrada = entrada;
        this.saida = saida;
    }

    public List<Volume> run() {
        saida.println("Digite o nome do um volume:");
        String nome = entrada.nextLine();
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Digite o nome do um volume.");
        }
        saida.println("Digite o número do um volume:");
        int numero = Integer.parseInt(entrada.nextLine());
        if (numero <= 0) throw new IllegalArgumentException("Digite o numero do um volume.");
        return consultarumVolume.buscar(nome, numero);
    }

}



