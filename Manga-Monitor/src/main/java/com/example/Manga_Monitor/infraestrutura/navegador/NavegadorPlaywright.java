package com.example.Manga_Monitor.infraestrutura.navegador;

import com.microsoft.playwright.*;

public class NavegadorPlaywright implements AutoCloseable {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    public NavegadorPlaywright() {
        Playwright playwright = Playwright.create();

            this.playwright = playwright;
            this.browser = playwright.chromium().launch();
            this.browserContext = browser.newContext(new Browser.NewContextOptions().setLocale("pt-BR").setViewportSize(1280,900));
            this.page = browserContext.newPage();



    }

    public Playwright getPlaywright() {
        return playwright;
    }

    public Browser getBrowser() {
        return browser;
    }

    public BrowserContext getBrowserContext() {
        return browserContext;
    }

    public Page getPage() {
        return page;
    }

    @Override
    public void close() {
        try {
            if (browserContext != null) {
                browserContext.close();
            }
        } finally {
            try {
                if (browser != null) {
                    browser.close();
                }
            } finally {
                if (playwright != null) {
                    playwright.close();
                }
            }
        }
    }
}