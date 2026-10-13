package com.example.Manga_Monitor.infraestrutura.navegador;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/*
 * EXERCÍCIO 1 — Ciclo de vida do navegador (sem implementação).
 * Objetivo: concentrar criação e encerramento dos recursos externos.
 * 4. Crie o Playwright e inicie Chromium usando a opção semJanela.
 *    Preserve o sandbox do navegador. Se a inicialização falhar, libere os
 *    recursos já criados e propague a causa.
 * 5. Em novoContexto, reutilize o Browser e devolva um contexto novo por chamada,
 *    com localidade pt-BR e viewport 1280 por 900.
 * 6. Em close, encerre Browser e Playwright, tentando liberar o segundo mesmo
 *    quando o primeiro falhar. Limpe as referências e permita chamadas repetidas.
 * 7. Use a instância sequencialmente na mesma thread. Quem cria o navegador
 *    encerra a rodada; quem recebe um contexto encerra aquela consulta.
 */
public class NavegadorPlaywright {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    public NavegadorPlaywright() {
        try (Playwright playwright = Playwright.create()
        ) {
            this.playwright = playwright;
            this.browser = playwright.chromium().launch();
            this.browserContext = browser.newContext();
            this.page = browserContext.newPage();
        }
    }

}