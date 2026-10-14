package com.example.Manga_Monitor.infraestrutura.navegador;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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
            this.browserContext = browser.newContext(new Browser.NewContextOptions().setLocale("pt-BR").setViewportSize(1280,900));
            this.page = browserContext.newPage();
        }


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
}