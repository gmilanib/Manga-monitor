package com.example.Manga_Monitor.infraestrutura.http;

import com.example.Manga_Monitor.consulta.ConsultorPaginaVolume;
import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.Volume;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
     * <p>
     * Atenção: o HTML da Amazon pode variar e bloquear automação. Grave HTMLs
     * de exemplo nos testes; não faça testes automatizados dependerem da rede.
     */

    private HttpClient client = HttpClient.newHttpClient();

    public void consulta() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.amazon.com.br/Fullmetal-Alchemist-20-Hiromu-Arakawa/dp/8545706820/ref=sr_1_1?__mk_pt_BR=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=1C74CGM35WYP7&dib=eyJ2IjoiMSJ9.I2Puyl91GdKQXpHkb9OF_Jqs3p_IvDTSEbdzLDoPlLcgX2nVyH_pXJT-3hm2jgbF1_ZJ84Z-WpWgrtovyTC8eqFLoJi6R0-WAuLnqmJXt-KCHpp2lwizdHtJG80yG-BLc0HvoqMp3__25V2NOei4rwmNz6sM0ahQBnPemgHxCRQ7gyHYL-iJlPXs7gGSBYXqqZpqes4KuP7luXxnbRQKHCyGhldmgdVnUaTWnCQ8t-bqSgjgvakL8FEfdjNV7GiYSbY49E_DB1Lnag3kRcceLHwNqiKhcJzt9jM4wzt-LVw.At4jotLCccj92sx1LXM0-3RwtRC9wO7lzq_LcOvWbKg&dib_tag=se&keywords=FMA+20&qid=1790677073&sprefix=fma+20o%2Caps%2C367&sr=8-1")).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Headers: " + response.headers().map());
            System.out.println("Response Body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public ResultadoPagina consultar(Volume volume) {
        throw new UnsupportedOperationException("Implemente a consulta HTTP e a extração");
    }
}
