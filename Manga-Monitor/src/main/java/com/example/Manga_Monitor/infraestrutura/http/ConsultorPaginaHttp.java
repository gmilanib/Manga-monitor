package com.example.Manga_Monitor.infraestrutura.http;

import com.example.Manga_Monitor.consulta.ConsultorPaginaVolume;
import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.Volume;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String html = "";
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
            Pattern patternWhole = Pattern.compile("class=\"a-price-whole\"[^>]*>\\s*([\\d.]+)");
            Pattern patternFraction = Pattern.compile("class=\"a-price-fraction\"[^>]*>\\s*(\\d+)");

            Matcher matcherWhole = patternWhole.matcher(html);
            Matcher matcherFraction = patternFraction.matcher(html);

            if (matcherWhole.find() && matcherFraction.find()) {

                String whole = matcherWhole.group(1);
                String fraction = matcherFraction.group(1);

                String preco = whole.replace(".", "") + "." + fraction;

                BigDecimal valor = new BigDecimal(preco);

                resultadoPagina = new ResultadoPagina(disponibilidade, valor, motivoDoErro);

            } else {
                motivoDoErro = "*Deu erro em algum lugar";
                BigDecimal valor = new BigDecimal("0");
                resultadoPagina = new ResultadoPagina(disponibilidade, valor, motivoDoErro);
            }

        }
        return resultadoPagina;
    }
}

