/*
 * EXERCÍCIO 5 — Reconstruir os testes (nenhum teste executável neste arquivo).
 *
 * Preparação, em passos:
 * 1. Declare pacote e classe de testes JUnit. Dê nomes que descrevam comportamento.
 * 2. Carregue a fixture /amazon/oferta-pix.html como UTF-8.
 * 3. Prepare Chromium e um contexto isolado por teste; injete a fábrica/contexto
 *    no consultor. Intercepte TODAS as requisições e responda com HTML local,
 *    redirecionamento ou falha controlada. Não dependa do site real.
 * 4. Separe preparação, ação e verificação. Compare BigDecimal sem usar double.
 * 5. Feche recursos mesmo se uma asserção falhar. Verifique também o fechamento
 *    realizado pelo consultor, antes da limpeza do próprio teste.
 *
 * Cenários positivos e resultados esperados:
 * - Oferta principal Pix/NuPay de 41,70 e estoque: DISPONIVEL e 41.70;
 *   ignorar referência 43,90, parcela 21,95, preço oculto 99,90 e outras ofertas.
 * - Valor com milhar e espaço não separável: 1.234,56 resulta em 1234.56.
 * - Valores principais repetidos e iguais: aceitar o valor, sem falso conflito.
 * - Contextos distintos: cookies e páginas de uma consulta não vazam para outra.
 *
 * Cenários de ausência e resultados esperados:
 * - Condição Pix/NuPay ausente: preço null, disponibilidade preservada.
 * - Bloco de preço principal ausente: preço null, sem capturar outras ofertas.
 * - 'Não disponível': INDISPONIVEL e preço null, nunca zero.
 * - Texto de estoque ausente: DESCONHECIDA, sem inventar disponibilidade.
 *
 * Cenários negativos e resultados esperados:
 * - HTTP 503: FalhaConsultaException mencionando o status.
 * - CAPTCHA com HTTP 200: falha de verificação de acesso, sem tentar resolver.
 * - Página sem produto: timeout convertido em falha com causa preservada.
 * - ASIN divergente no HTML ou redirecionamento para outro produto: falha.
 * - Título/ASIN ausente: falha de identificação do produto.
 * - Preço zero, negativo ou malformado: falha, sem fabricar um preço.
 * - Dois preços principais visíveis diferentes: falha por ambiguidade.
 * - Outra loja, HTTP, credenciais, porta inválida ou ASIN inválido na URL:
 *   rejeição antes de abrir um contexto ou navegar.
 * - Timeout zero, negativo, infinito ou NaN: rejeição na construção.
 * - Conexão abortada: falha de navegador com causa preservada.
 * - Falha ao iniciar Chromium: recursos parcialmente criados são encerrados.
 * - Sucesso e falha durante consulta: contexto/páginas sempre encerrados.
 * - Fechamento repetido do navegador: não falha por recurso já liberado.
 *
 * Implemente um cenário por vez. Acrescente fixtures específicas quando necessário.
 * Os cenários acima são especificação pendente, não cobertura automatizada atual.
 */
