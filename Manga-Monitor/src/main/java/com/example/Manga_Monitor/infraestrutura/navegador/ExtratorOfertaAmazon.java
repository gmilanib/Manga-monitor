package com.example.Manga_Monitor.infraestrutura.navegador;

import com.example.Manga_Monitor.dominio.TentativaConsulta;
import com.example.Manga_Monitor.dominio.Volume;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class ExtratorOfertaAmazon {
    private NavegadorPlaywright navegadorPlaywright = new NavegadorPlaywright();
    private BrowserContext browserContext;
    private TentativaConsulta tentativaConsulta;

    public ExtratorOfertaAmazon() {
        this.browserContext = navegadorPlaywright.getBrowserContext();
    }

    public TentativaConsulta ExtrairAmazon(Volume volume) {

        Page page = browserContext.newPage();
        page.navigate(volume.getURL());

        return null;
    }

}