package com.example.Manga_Monitor.apresentacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.VolumeJsonRepository;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Realiza a entrada e a saída do cadastro pelo terminal.
 * <p>
 * TODO para conduzir um cadastro:
 * 1. declarar uma dependência do tipo CadastrarVolume;
 * 2. receber essa dependência pelo construtor;
 * 3. criar um método público que inicie a interação pelo console;
 * 4. solicitar o título ao usuário;
 * 5. solicitar o número como texto e convertê-lo para inteiro;
 * 6. solicitar a URL;
 * 7. chamar o caso de uso CadastrarVolume com os três dados;
 * 8. informar sucesso somente quando o caso de uso terminar sem exceção;
 * 9. tratar número não inteiro, dados inválidos e falha de persistência com
 *    mensagens diferentes e compreensíveis.
 * <p>
 * Esta classe controla somente entrada e saída. Ela não deve construir o
 * repository, usar ObjectMapper nem acessar diretamente o arquivo JSON.
 */
public class CadastroVolumeConsole {
    private Path filePath;

    public CadastroVolumeConsole(Path filePath) {
        this.filePath = filePath;
    }

    public void cadastrarNovoVolume() {
        VolumeJsonRepository volumeRepository = new VolumeJsonRepository(filePath);
        Scanner sc = new Scanner(System.in);

        System.out.println("########## Cadastrando Volume ##########");
        System.out.println("Nome do Volume: ");
        String nome = sc.nextLine();
        System.out.println("Volume: ");
        int num = sc.nextInt();
        System.out.println("URL: ");
        sc.nextLine();
        String url = sc.nextLine();
        Volume volume = new Volume(nome, num, url);

        volumeRepository.salvar(volume);

        sc.close();
    }
}
