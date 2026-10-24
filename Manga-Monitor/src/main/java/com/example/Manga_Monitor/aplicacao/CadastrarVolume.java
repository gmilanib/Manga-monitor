package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.VolumeRepository;

import java.util.Scanner;

public class CadastrarVolume {
    private final VolumeRepository volumeRepository;
    private final Scanner sc;

    public CadastrarVolume(VolumeRepository volumeRepository) {
        this(volumeRepository, new Scanner(System.in));
    }

    public CadastrarVolume(VolumeRepository volumeRepository, Scanner sc) {
        this.volumeRepository = volumeRepository;
        this.sc = sc;
    }

    public void cadastrarNovoVolume() {
        System.out.println("########## Cadastrando Volume ##########");
        System.out.println("*****Nome do Volume***** ");
        String nome = sc.nextLine();
        System.out.println("*****Volume*****: ");
        int num = sc.nextInt();
        System.out.println("*****URL*****");
        sc.nextLine();
        String url = sc.nextLine();
        Volume volume = new Volume(nome, num, url);

        volumeRepository.save(volume);
        sc.close();
    }
}
