package com.example.Manga_Monitor.dominio;

import java.math.BigDecimal;

public class ResultadoPagina {
    private boolean paginaDisponnivel;
    private BigDecimal preco;
    private String motivoErro;

    public ResultadoPagina(boolean paginaDisponnivel, BigDecimal preco, String motivoErro) {
        this.paginaDisponnivel = paginaDisponnivel;
        this.preco = preco;
        if (paginaDisponnivel == true) {
            this.motivoErro = "";
        } else {
            this.motivoErro = "*Montar motivo do erro no cliente HTTP";
        }
    }

    public boolean isPaginaDisponnivel() {
        return paginaDisponnivel;
    }

}
