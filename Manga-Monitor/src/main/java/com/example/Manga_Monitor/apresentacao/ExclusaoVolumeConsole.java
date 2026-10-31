package com.example.Manga_Monitor.apresentacao;

import com.example.Manga_Monitor.aplicacao.ExcluirVolume;
import com.example.Manga_Monitor.dominio.Volume;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class ExclusaoVolumeConsole {
    private final ExcluirVolume excluirVolume;
    private final Scanner entrada;
    private final PrintStream saida;

    public ExclusaoVolumeConsole(ExcluirVolume excluirVolume, Scanner entrada, PrintStream saida) {
        this.excluirVolume = excluirVolume;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void executar() {
        saida.println("########## Excluir volume ##########");
        saida.println("Informe parte do título:");
        if (!entrada.hasNextLine()) return;
        String titulo = entrada.nextLine();
        if (titulo.isBlank()) {
            saida.println("Informe ao menos parte do título.");
            return;
        }
        saida.println("Número do volume:");
        if (!entrada.hasNextLine()) return;
        int numero;
        try {
            numero = Integer.parseInt(entrada.nextLine().trim());
            if (numero <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            saida.println("Informe um número inteiro maior que zero. Nenhum cadastro excluído.");
            return;
        }
        List<Volume> encontrados = excluirVolume.buscar(titulo, numero);
        if (encontrados.isEmpty()) {
            saida.println("Nenhum volume encontrado.");
            return;
        }
        for (int i = 0; i < encontrados.size(); i++) {
            Volume volume = encontrados.get(i);
            saida.printf("%d. %s — volume %d — %s%n", i + 1,
                    volume.getTitulo(), volume.getNumero(), volume.getURL());
        }
        saida.println("Escolha o número do cadastro (0 para cancelar):");
        if (!entrada.hasNextLine()) return;
        String escolha = entrada.nextLine().trim();
        if (escolha.equals("0")) {
            saida.println("Exclusão cancelada.");
            return;
        }
        int indice;
        try {
            indice = Integer.parseInt(escolha);
        } catch (NumberFormatException e) {
            saida.println("Seleção inválida. Nenhum cadastro excluído.");
            return;
        }
        if (indice < 1 || indice > encontrados.size()) {
            saida.println("Seleção inválida. Nenhum cadastro excluído.");
            return;
        }
        Volume selecionado = encontrados.get(indice - 1);
        saida.printf("Excluir %s, volume %d, URL %s e todo seu histórico? Digite SIM para confirmar:%n",
                selecionado.getTitulo(), selecionado.getNumero(), selecionado.getURL());
        if (!entrada.hasNextLine() || !entrada.nextLine().trim().equalsIgnoreCase("SIM")) {
            saida.println("Exclusão cancelada.");
            return;
        }
        try {
            if (excluirVolume.excluir(selecionado.getId())) {
                saida.println("Volume e histórico excluídos com sucesso.");
            } else {
                saida.println("O cadastro selecionado não existe mais.");
            }
        } catch (RuntimeException e) {
            saida.println("Não foi possível concluir a exclusão. A transação foi revertida.");
        }
    }
}
