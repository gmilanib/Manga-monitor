/*
 * EXERCÍCIO 2 — Interpretação da oferta (sem implementação).
 * Objetivo: receber Page e ASIN esperado e devolver ResultadoPagina.
 * Esta classe interpreta o DOM; não navega, não salva no banco e não lê terminal.
 *
 * 1. Declare o pacote e crie ExtratorOfertaAmazon, com uma operação extrair.
 * 2. Confirme título não vazio em #productTitle e identidade do produto em
 *    #ASIN ou input[name='ASIN']. Divergência ou ausência gera falha de consulta.
 * 3. Leia o primeiro #availability visível. Normalize espaços comuns e não
 *    separáveis e compare sem depender de maiúsculas/minúsculas.
 * 4. Para indicação explícita de indisponibilidade, devolva INDISPONIVEL e
 *    preço ausente. Para 'em estoque', use DISPONIVEL; sem indicação confiável,
 *    use DESCONHECIDA. Nunca deduza estoque apenas da presença de preço.
 * 5. Restrinja a pesquisa à oferta principal visível, começando por #buybox.
 *    Sem esse bloco ou sem condição Pix/NuPay, mantenha o preço ausente.
 * 6. Investigue os blocos de preço principal da fixture: corePriceDisplay,
 *    corePrice, priceToPay e apexPriceToPay. Centralize os seletores aqui.
 *    Considere apenas blocos visíveis, lendo o texto monetário .a-offscreen
 *    dentro deles; esse texto pode ser visualmente oculto por acessibilidade.
 * 7. Ignore preço de referência, parcelas, recomendações e outros vendedores
 *    fora da oferta principal. Não procure simplesmente o menor preço da página.
 * 8. Converta moeda brasileira para BigDecimal: remova símbolo e separador de
 *    milhar e converta vírgula decimal. Exija formato completo e valor positivo.
 *    Exemplo esperado: R$ 1.234,56 resulta em 1234.56, sem usar double.
 * 9. Reúna valores distintos da oferta principal. Repetições do mesmo valor
 *    não são conflito; valores diferentes geram FalhaConsultaException.
 * 10. Se não houver preço elegível, devolva preço ausente, nunca zero.
 *     Retorne o resultado usando o estado de disponibilidade já identificado.
 *
 * Verificação: oferta Pix de 41,70 vence referências de 43,90, parcelas de
 * 21,95 e ofertas externas de 30,00. Conflito não deve escolher o menor valor.
 * Os seletores são pontos de partida do exercício, não garantia sobre o site atual.
 */
