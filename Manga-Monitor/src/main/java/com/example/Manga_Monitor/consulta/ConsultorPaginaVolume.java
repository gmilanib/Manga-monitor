package com.example.Manga_Monitor.consulta;

import com.example.Manga_Monitor.dominio.ResultadoPagina;
import com.example.Manga_Monitor.dominio.Volume;

/** Consulta a oferta sem expor navegador ou HTML à aplicação. */
public interface ConsultorPaginaVolume {
    /** Retorna Pix/NuPay ou preço ausente; falha técnica lança FalhaConsultaException, nunca null. */
    ResultadoPagina consultar(Volume volume);
}
