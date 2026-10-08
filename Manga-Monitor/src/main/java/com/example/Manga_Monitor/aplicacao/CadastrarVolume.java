package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.VolumeRepository;

import java.util.Scanner;

public class CadastrarVolume {
    private final VolumeRepository volumeRepository;

    public CadastrarVolume(VolumeRepository volumeRepository) {
        this.volumeRepository = volumeRepository;
    }

    public void cadastrarNovoVolume() {
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

        volumeRepository.save(volume);
        sc.close();
    }
}
