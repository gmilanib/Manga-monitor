package com.example.Manga_Monitor.consulta;

/** Falha técnica da consulta; não significa que o produto está sem estoque. */
public class FalhaConsultaException extends RuntimeException {
    public FalhaConsultaException(String mensagem) {
        super(mensagem);
    }

    public FalhaConsultaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
