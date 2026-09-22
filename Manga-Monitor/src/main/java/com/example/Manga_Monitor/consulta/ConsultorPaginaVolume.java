package com.example.Manga_Monitor.consulta;

import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.Volume;

/**
 * Porta usada pela aplicação para consultar uma página sem depender da
 * tecnologia HTTP nem da estrutura HTML de uma loja específica.
 */
public interface ConsultorPaginaVolume {

    /**
     * TODO para o contrato:
     * 1. receber o Volume cuja URL será consultada;
     * 2. devolver preço e disponibilidade quando a página for interpretada;
     * 3. lançar uma exceção clara em timeout, status HTTP inesperado ou HTML
     *    que não possa ser interpretado;
     * 4. nunca converter falha técnica em produto indisponível.
     */
    ResultadoPagina consultar(Volume volume);
}
