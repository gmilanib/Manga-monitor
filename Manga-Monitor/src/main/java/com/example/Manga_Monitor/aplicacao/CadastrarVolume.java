package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.VolumeJsonRepository;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Coordena o caso de uso de cadastro de um volume.
 *
 * TODO para executar o caso de uso:
 * 1. declarar uma dependência do tipo VolumeRepository;
 * 2. receber essa dependência pelo construtor;
 * 3. criar um método público que receba título, número e URL;
 * 4. construir um Volume com esses dados;
 * 5. solicitar ao VolumeRepository que salve o Volume criado;
 * 6. deixar as exceções de validação e persistência chegarem à apresentação,
 *    que será responsável por transformá-las em mensagens para o usuário.
 *
 * Esta classe coordena o fluxo. Ela não deve conhecer Scanner, terminal,
 * ObjectMapper, JSON ou caminhos de arquivos.
 */
public class CadastrarVolume {
    private Path filePath;

    public CadastrarVolume(Path filePath) {
        this.filePath = filePath;
    }

    public void cadastrarNovoVolume() {
        VolumeJsonRepository volumeRepository = new VolumeJsonRepository(filePath);
        Scanner sc = new Scanner(System.in);

        System.out.println("########## Cadastrando Volume ##########");
        System.out.println("*****Nome do Volume***** ");
        String nome = sc.nextLine();
        System.out.println("*****Volume*****: ");
        int num = sc.nextInt();
        System.out.println("*****URL*****");
        sc.nextLine();
        String url = sc.nextLine();
        Volume volume = new Volume(nome, num, url);

        volumeRepository.salvar(volume);
        sc.close();
    }
}
