package com.example.Manga_Monitor.infraestrutura.http;

import com.example.Manga_Monitor.consulta.ConsultorPaginaVolume;
import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.Volume;

/**
 * Adaptador que futuramente fará a requisição HTTP e extrairá a oferta.
 * Para o primeiro volume, pode reconhecer apenas o HTML da Amazon; seletores
 * de outras lojas devem ficar fora deste primeiro incremento.
 */
public class ConsultorPaginaHttp implements ConsultorPaginaVolume {

    /**
     * TODO ao implementar:
     * 1. criar/injetar um java.net.http.HttpClient;
     * 2. converter volume.getURL() em URI;
     * 3. montar GET com timeout e User-Agent identificável;
     * 4. enviar a requisição e conferir o código HTTP;
     * 5. entregar o HTML a um método privado de extração;
     * 6. localizar disponibilidade e preço sem confundir ausência de preço
     *    com valor zero;
     * 7. normalizar o preço brasileiro e convertê-lo para BigDecimal;
     * 8. devolver ResultadoPagina;
     * 9. preservar a causa de timeout, interrupção ou HTML inesperado.
     *
     * Atenção: o HTML da Amazon pode variar e bloquear automação. Grave HTMLs
     * de exemplo nos testes; não faça testes automatizados dependerem da rede.
     */
    @Override
    public ResultadoPagina consultar(Volume volume) {
        throw new UnsupportedOperationException("Implemente a consulta HTTP e a extração");
    }
}
