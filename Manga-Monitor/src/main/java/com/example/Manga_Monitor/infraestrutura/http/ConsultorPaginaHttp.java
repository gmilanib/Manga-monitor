package com.example.Manga_Monitor.infraestrutura.http;

import com.example.Manga_Monitor.consulta.ConsultorPaginaVolume;
import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.Volume;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultorPaginaHttp implements ConsultorPaginaVolume {
    private ResultadoPagina resultadoPagina;
    private HttpClient client = HttpClient.newHttpClient();

    /**
     * 5. entregar o HTML a um método privado de extração;
     * 6. localizar disponibilidade e preço sem confundir ausência de preço
     * com valor zero;
     * 7. normalizar o preço brasileiro e convertê-lo para BigDecimal;
     * 8. devolver ResultadoPagina;
     * 9. preservar a causa de timeout, interrupção ou HTML inesperado.
     */

    @Override
    public ResultadoPagina consultar(Volume volume) {
        boolean disponibilidade = false;
        String html;
        String motivoDoErro;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(volume.getURL())).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            disponibilidade = response.statusCode() == 200;
            html = response.body();
            System.out.println(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (disponibilidade) {
            motivoDoErro = "";
            BigDecimal valor = new BigDecimal("10.00");


        } else {
            motivoDoErro = "*Deu erro em algum lugar";
            BigDecimal valor = new BigDecimal("10.00");

        }

        BigDecimal preco = new BigDecimal("10.00");
        resultadoPagina = new ResultadoPagina(disponibilidade, preco, null);
        return resultadoPagina;
    }


}
