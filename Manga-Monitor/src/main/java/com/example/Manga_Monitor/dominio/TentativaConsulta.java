package com.example.Manga_Monitor.dominio;

import com.example.Manga_Monitor.infraestrutura.navegador.NavegadorPlaywright;
import com.microsoft.playwright.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TentativaConsulta {
    private Volume volume;
    private LocalDateTime data;
    private Disponibilidade disponibilidade;
    private BigDecimal valor;

    public TentativaConsulta(Volume volume) {
        this.volume = volume;
        this.data = LocalDateTime.now();
    }

    public void tentativaConsulta(Volume volume){
        NavegadorPlaywright navegadorPlaywright = new NavegadorPlaywright();
        Page page =navegadorPlaywright.getPage();
        page.navigate(volume.getURL());

    }


}
