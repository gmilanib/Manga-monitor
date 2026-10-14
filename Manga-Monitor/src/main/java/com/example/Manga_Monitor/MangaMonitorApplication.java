package com.example.Manga_Monitor;

import com.example.Manga_Monitor.aplicacao.ConsultarVolume;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MangaMonitorApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("========== MangaMonitorApplication ==========");
        System.out.println("Selecione a opção dejeada: \n1) CADASTRAR \n2) CONSULTAR");

        int opt = 2;

        switch (opt) {
            case 1:
                break;
            case 2:
                ConsultarVolume consultarVolume = new ConsultarVolume();
                break;
            default:
                break;
        }

    }
}

