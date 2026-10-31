# Aprendizagem — Manga Monitor

## Objetivo e acordos

Aprender arquitetura, engenharia de software, Java e Spring construindo um
monitor local de preços de mangás. O usuário implementa; o agente orienta,
fornece ajuda gradual e avalia. Código completo e alterações no código exigem
pedido explícito. Perguntas devem ser interativas e agrupadas.

## Estado em 16/09/2026

Planejamento; funcionalidades abaixo são requisitos, não implementação verificada.
O pom.xml declara Java 21, Spring Boot 4.1.1, JPA, Validation e H2.
PostgreSQL e JavaFX são decisões do projeto ainda não refletidas nesse arquivo.
Experiência: usa classes e objetos; deseja praticar interfaces e exceções.
Ainda não trabalhou com Spring nem escreveu testes automatizados.
Disponibilidade: 15–30 minutos por sessão.
Nenhuma entrega avaliada; nenhuma nota atribuída; testes não executados nesta
etapa de documentação e diagnóstico.

## Requisitos acordados para a V1

- Desktop JavaFX, TUI futura; terminologia própria em português.
- Cadastro manual de URL Amazon, título e número do volume.
- Uma URL por volume; validação inicial com um volume, depois ampliação.
- Scraping da oferta principal, inclusive de terceiros; preço e disponibilidade.
- Registrar toda tentativa, inclusive falhas e preços repetidos.
- Histórico permanente em PostgreSQL local; um usuário.
- Intervalo global em horas inteiras, mínimo de uma hora.
- Consultar ao iniciar e repetir pelo intervalo; falhas aguardam o próximo
  intervalo, com consulta manual disponível.
- Inicialização manual; fechar janela mantém execução; sair encerra.
- Cadastrar, listar, pausar e retomar; sem edição ou exclusão na V1.
- CSV manual e automático após toda tentativa; um arquivo com histórico completo.
- Primeiro backup após salvar a primeira tentativa do primeiro volume.
- Backup semanal pela aplicação, contado desde o último sucesso; recuperar
  atraso na inicialização e manter os três arquivos mais recentes.

## Roteiro proposto, sujeito ao diagnóstico

1. Cadastrar um volume e apresentar uma consulta simulada em um fluxo demonstrável.
2. Salvar e recuperar o histórico local no PostgreSQL.
3. Consultar uma URL real e apresentar preço, disponibilidade ou falha.
4. Operar pelo desktop com consultas periódicas e pausa/retomada.
5. Exportar histórico e gerar/restaurar backups.

Cada MVP será dividido em desafios de aproximadamente 15–30 minutos,
com no máximo 250 linhas humanas incluindo testes. Um desafio detalhado por vez.

## Etapa atual

Diagnóstico concluído. O aluno atribuiu leitura de campos à apresentação,
coordenação à aplicação, extração de HTML à infraestrutura e salvamento à
aplicação. As três primeiras associações estão adequadas; a última exige
distinguir solicitar o salvamento (aplicação) de executá-lo no banco
(infraestrutura). Exercício diagnóstico sem nota.

Desafio atual: 01 — descrever responsabilidades do primeiro fluxo, sem código.
Objetivo: distinguir coordenação de execução técnica. Pré-requisito: classes
e objetos e explicação de portas/contratos apresentada na conversa.
Entrega na conversa: 6–10 passos, com responsável e dados transmitidos, e
previsão dos três cenários abaixo. Tempo: 15–30 minutos. Arquivos de código: nenhum.
Escopo: cadastro e primeira consulta, supondo cadastro válido e banco operacional;
CSV, backup, agendamento e implementação da tela ficam fora deste exercício.

Critérios observáveis: separar tela, coordenação, extração e gravação; indicar
dados atravessando os limites; preservar diferença entre falha e indisponibilidade;
explicar o que muda se JavaFX for substituído por TUI.

Cenários de mesa, não testes executados:
- Positivo: página informa disponível e R$ 39,90; guardar preço, disponibilidade
  e instante, com resultado de consulta bem-sucedida.
- Borda: página informa indisponível e sem preço; guardar indisponibilidade e
  preço ausente, nunca zero.
- Negativo: tempo de resposta excedido; guardar falha e instante, sem inventar
  preço ou concluir indisponibilidade.

Rubrica adaptada ao exercício conceitual: coerência do fluxo 4; cenários de
mesa 2; clareza e organização 2; compreensão e variação TUI 2. Não exige código
nem testes automatizados; rubrica apresentada antes da entrega. Avaliação pendente.

Rubrica futura: funcionamento 4; testes 2; clareza e organização 2;
compreensão demonstrada 2. Critérios pendentes não contam como falhas comprovadas.

## Ajuda e evidências

- Conversa: entrevista de requisitos concluída e estrutura de pastas proposta.
- Inspeção: pom.xml e inventário dos arquivos iniciais.
- Orientação inicial: separar entrada de dados, coordenação da consulta e
  acesso a recursos externos; detalhamento após diagnóstico.
- Fonte metodológica: skill aprendizagem e references/avaliacao.md locais.
- Fonte técnica consultada: Alistair Cockburn, artigo original sobre portas e
  adaptadores: https://alistair.cockburn.us/hexagonal-architecture
- Ajuda após diagnóstico: explicação da diferença entre solicitar e executar
  a persistência; contrato permite substituir tecnologia externa.

## Próximo passo

Desafio 01 concluído com 10/10. Desafio 02 tem evidências suficientes para
avançar; pendências pontuais serão retomadas na implementação, sem exigir
reenvio de respostas. Desafio 03 atribuído: implementar Volume e seus testes.

## Avaliação 01 — 17/09/2026 (provisória)

Entrega na conversa: seis passos, cadastro na aplicação, leitura e gravação
como aplicação/infraestrutura, chamada da URL na aplicação, extração na
infraestrutura e exibição na apresentação. Propôs guardar status da consulta
e da obra, valor e motivo da falha. Identificou reaproveitamento das camadas
fora da apresentação ao trocar GUI por TUI.

Evidências: boa sequência e reconhecimento das fronteiras. Chamada da URL
precisa distinguir solicitar consulta de executar HTTP. Leitura e gravação
precisam explicitar essa mesma divisão. Dados transmitidos entre passos e
resultados individuais dos três cenários ainda não foram detalhados.

Pontuação comprovada: fluxo 3/4; cenários 0,5/2; clareza 2/2;
compreensão 1,5/2. Total provisório: 7,0; intervalo possível 7,0–10,0.
Os pontos restantes estão pendentes, não são erros comprovados.
Não penalizar ortografia ou numeração. Não há implementação para executar;
testes automatizados não aplicáveis a esta entrega conceitual.

Ajuda: explicar HTTP como execução técnica, diferença entre consulta falha
e produto indisponível e ausência de necessidade de reler um cadastro já
disponível em memória. Fonte primária consultada novamente:
https://alistair.cockburn.us/hexagonal-architecture

Complemento solicitado: responsável por HTTP e dados recebidos/devolvidos;
registros para disponível a R$ 39,90, indisponível sem preço e timeout;
justificativa de como a aplicação permanece independente do JavaFX.
Sem novo desafio atribuído e sem alteração no código.

### Complemento 01 — 17/09/2026

O aluno identificou que a infraestrutura recebe a URL e devolve disponibilidade
e preço à aplicação. Para os cenários: disponível com 39,90 e sem erro;
indisponível sem preço e sem erro; falha com status e preço ausentes e indicação
de erro. Declarou não conhecer JavaFX/TUI para justificar o reaproveitamento.

Progresso: contrato do coletor compreendido e ausência de preço distinguida
de zero. Falta separar explicitamente resultado da consulta e disponibilidade,
incluir instante e identificar motivo da falha. Falta de familiaridade com UI
não é erro arquitetural: oferecer exemplo com valores versus componente visual.

Pontuação provisória atual: fluxo 4/4; cenários 1/2; clareza 2/2;
compreensão 1,5/2. Comprovados 8,5/10; intervalo 8,5–10,0.
Mantidas avaliações anteriores. Testes automatizados não aplicáveis.

Ajuda fornecida: tabela dos três resultados distinguindo sucesso/falha,
disponibilidade e instante; explicação de receber texto comum em vez de
componente JavaFX. Próximo passo: aluno justificar, em palavras próprias,
por que receber uma URL como texto permite reutilizar o caso de uso em outra UI.

### Complemento 02 — 17/09/2026

O aluno explicou corretamente que consultar uma página que informa falta de
estoque é sucesso da consulta, com produto indisponível naquele momento.
Sobre reaproveitamento, relacionou-o à leitura do HTML sem renderização.
Essa explicação ainda mistura a entrada da interface com a coleta externa.
Ajuda: mostrar que GUI e TUI fornecem a mesma String ao mesmo caso de uso;
o coletor de HTML é uma responsabilidade distinta, já compreendida.

Pontuação provisória mantida em 8,5/10 (fluxo 4; cenários 1; clareza 2;
compreensão 1,5). Distinção sucesso/indisponibilidade confirmada. Pendentes:
aplicação independente do componente visual e registro completo de falha
com instante, disponibilidade desconhecida e motivo específico.
Próximo passo: comparar parâmetro String com campo visual e completar o
registro de um timeout. Sem código alterado; testes não aplicáveis.

### Complemento 03 — 17/09/2026

O aluno completou o cenário de timeout: resultado erro, disponibilidade
desconhecida, preço desconhecido, data/hora, indicador de falha e motivo
específico. Cenários agora suficientemente demonstrados; preço desconhecido
interpretado como valor ausente, sem introduzir texto em campo monetário.

Na variação TUI, afirmou que ela não conseguiria renderizar campo JavaFX.
Identifica a incompatibilidade prática, mas ainda associa dependência a
renderização. Esclarecimento: exigir objeto JavaFX impõe dependência mesmo
sem exibi-lo; String permite passar o dado sem construir componente visual.

Pontuação comprovada atual: fluxo 4/4; cenários 2/2; clareza 2/2;
compreensão 1,5/2. Nota provisória 9,5/10, intervalo 9,5–10,0.
Pergunta final localizada: escolher entre receber String ou campo JavaFX
quando a URL vier de arquivo e justificar brevemente. Sem novo desafio.
Documentação atualizada; nenhuma alteração de código, testes não aplicáveis.

### Complemento 04 — 17/09/2026 — conclusão

Ao considerar uma URL vinda de arquivo, o aluno escolheu String e relacionou
esse dado à requisição HTTP e à obtenção do HTML para extrair o preço.
A resposta, junto às evidências anteriores, demonstra escolha do dado comum
em vez do componente visual. Orientação final: a mesma String pode vir de
arquivo, terminal ou JavaFX; o caso de uso solicita a consulta e a infraestrutura
executa HTTP e extrai os dados. Não é necessário renderizar a página para
explicar a independência entre caso de uso e interface.

Avaliação final do desafio 01: fluxo 4/4; cenários 2/2; clareza 2/2;
compreensão 2/2. Total: 10/10. Avaliações provisórias preservadas acima.
Desafio conceitual concluído; isso não comprova implementação funcional.
Nenhum código alterado; testes automatizados não aplicáveis a esta entrega.
Próximo passo sugerido: modelar os dados do primeiro fluxo em um exercício
curto, antes de implementar. Nenhum novo desafio detalhado atribuído ainda.

## Desafio 02 — dados do cadastro e do histórico — 17/09/2026

Objetivo: separar dados do volume acompanhado dos dados produzidos em cada
tentativa de consulta. Exercício conceitual de 15–30 minutos; sem código.
Pré-requisitos: classes e objetos, responsabilidades discutidas no desafio 01.
Não exige conhecimento de JPA, SQL, JavaFX nem sintaxe de tipos Java.

Escopo: cadastro manual de título, número do volume e URL; histórico de todas
as tentativas, inclusive falhas e preços repetidos. Um volume pode ter várias
tentativas; cada tentativa pertence a um volume. CSV, backup, agendamento,
identificadores técnicos e implementação de persistência ficam fora da entrega.
Os nomes usados neste exercício são provisórios, não um modelo implementado.

Entrega na conversa:
1. Dois grupos, Volume e Tentativa de consulta, com os campos propostos.
2. Para cada campo, indicar tipo em linguagem comum (texto, inteiro, decimal,
   data/hora ou conjunto de opções) e se pode ficar ausente, explicando quando.
3. Explicar como associar as tentativas ao volume, sem exigir detalhes de banco.
4. Representar três tentativas do mesmo volume: disponível por R$ 39,90;
   disponível novamente por R$ 39,90; timeout. Usar instantes distintos.

Pergunta de raciocínio incluída na entrega: por que guardar somente o último
preço no volume não atende ao histórico permanente acordado?

Critérios e rubrica apresentados antes da entrega: separação cadastro/histórico
3; campos, tipos e ausência coerentes 3; três exemplos preservando repetição e
falha 2; relação entre os grupos e justificativa do histórico 2. Total 10.
Não avaliar sintaxe Java, escolhas de ORM ou detalhes não solicitados.
Disponibilidade desconhecida não equivale a indisponibilidade; preço ausente
não é zero. Avaliar resultado da consulta separadamente da disponibilidade.

Ajuda inicial: cadastro descreve o que será acompanhado; tentativa registra o
que ocorreu em determinado instante. Exemplo de formato: título — texto —
obrigatório. Oferecer ajuda gradual sem entregar o modelo completo.

Estado: atribuído, entrega e avaliação pendentes. Nenhum código alterado.
Validação desta etapa por cenários de mesa; testes automatizados não aplicáveis.

### Entrega parcial 01 — desafio 02 — 17/09/2026

O aluno propôs Volume com obra (texto de 50 caracteres), número inteiro,
URL, identificador textual ou inteiro e busca ativa booleana. Para Tentativa:
data, hora, sucesso booleano, disponibilidade booleana, preço Float/Double,
frete Float/Double e motivo de erro limitado a 50 caracteres. Considerou
apenas frete e motivo do erro opcionais.

Evidências: separação adequada entre cadastro e histórico; busca ativa está
alinhada a pausar/retomar. Identificador técnico não é exigido no exercício.
Pendências: preço pode estar ausente tanto na indisponibilidade quanto na
falha; disponibilidade deve admitir desconhecida em falhas. Booleano pode
representar disponível/indisponível se admitir ausência explicitamente;
alternativa didática: opções disponível, indisponível e desconhecida.
Orientação: dinheiro deve usar representação decimal (BigDecimal em Java),
pois Float/Double usam aproximação binária. Data e hora separadas são possíveis;
um único instante de consulta simplifica representar quando a tentativa ocorreu.
Motivo do erro pode faltar no sucesso, mas deve explicar uma falha.
Limites de 50 caracteres propostos ainda não foram justificados nem aprovados.

Frete é proposta nova, não requisito acordado: esclarecer inclusão na V1 antes
de modelar coleta, ausência e eventual composição com preço. Não confundir
frete desconhecido com frete grátis. Relação tentativa/volume, três exemplos
e justificativa do histórico ainda aguardam resposta. Avaliação final pendente;
não tratar itens ainda não respondidos como erros demonstrados.

Próxima interação: revisar ausência/preço/disponibilidade, esclarecer intenção
sobre frete e completar a segunda pergunta do desafio. Sem código alterado;
testes automatizados não aplicáveis à entrega conceitual. Orientação apoiada
na skill domain-modeling; propostas do aluno ainda não são modelo implementado.

### Entrega parcial 02 — desafio 02 — 18/09/2026

O aluno apresentou três tentativas com identificadores distintos para FMA,
volume 19. As duas primeiras registram sucesso, disponibilidade e preço
39,99; a terceira registra falha, disponibilidade ausente e motivo Timeout
na requisição, mas mantém indevidamente preço 39,99. Todos os exemplos usam
18/09/2026 às 00:00, sem a variação de instante solicitada.

Acertos: preserva tentativas com preço repetido, distingue falha de
indisponibilidade e justifica manter registros anteriores para ter histórico.
A diferença entre 39,90 do enunciado e 39,99 do exemplo não prejudica o conceito.
Decisão explícita do aluno: frete fora do escopo da V1.

Orientação: preço na tentativa de timeout deve estar ausente, pois não foi
obtido naquela consulta; um último preço conhecido pode ser encontrado em
uma tentativa anterior bem-sucedida, sem copiá-lo como resultado da falha.
Instantes sugeridos para o exercício: 00:00, 01:00 e 02:00 do mesmo dia.
Obra e número identificam o volume nos exemplos, mas o ID da consulta
identifica apenas a tentativa. Para explicitar a relação, usar uma referência
ao mesmo volume cadastrado em cada tentativa, sem exigir SQL ou JPA.

Próxima entrega localizada: reescrever somente a tentativa 3 com horário
distinto, preço ausente e referência ao volume; indicar qual referência seria
compartilhada pelas três tentativas. Tipo decimal do preço ainda não confirmado
pelo aluno. Avaliação final pendente, sem nova nota atribuída nesta interação.
Nenhum código alterado; testes automatizados não aplicáveis ao exercício.

### Complemento de decisões — desafio 02 — 18/09/2026

O aluno escolheu explicitamente disponibilidade com três opções: disponível,
indisponível e desconhecida; confirmou que preço pode ficar ausente. Essa
resposta resolve a pendência conceitual do preço presente no exemplo de timeout:
o resultado esperado passa a ser falha, disponibilidade desconhecida, preço
ausente e motivo Timeout na requisição. Não exigir repetição dessa decisão.
Confirmou novamente que frete fica para depois da V1, sem definir suas regras.

Pendências restantes da entrega: explicitar referência comum ao volume nas
três tentativas, variar seus horários e confirmar tipo decimal para dinheiro.
Pergunta final agrupada: considerando volume cadastrado com ID 10, distinguir
esse ID dos IDs 1, 2 e 3 das tentativas e informar o tipo do preço. Orientação
sobre horários: usar 00:00, 01:00 e 02:00 como três instantes distintos.
Avaliação final ainda pendente. Nenhum código alterado; testes automatizados
não aplicáveis à atividade conceitual.

## Desafio 03 — primeira implementação de Volume — 18/09/2026

Motivação: o aluno solicitou exercícios práticos; a orientação reconheceu
excesso de prolongamento conceitual. Avançar com ajuda gradual no código,
sem exigir novas respostas conceituais já fornecidas. O aluno implementa;
o agente não deve entregar solução completa nem editar código sem pedido.

Objetivo: criar um Volume válido usando Java puro e verificar suas regras
com JUnit. Tempo estimado: 15–30 minutos, ajustável à primeira experiência
com testes. Limite conjunto: 250 linhas de código humano, incluindo testes.

Arquivos propostos dentro da aplicação:
- src/main/java/com/example/Manga_Monitor/dominio/Volume.java
- src/test/java/com/example/Manga_Monitor/dominio/VolumeTest.java

Recorte didático, não mudança de escopo da V1: título da obra (String),
número do volume (int) e URL (String). Construtor recebe os três dados;
campos privados e métodos de leitura. Sem setters nesta etapa. Busca ativa,
identificador, consultas HTTP, JPA, tela e persistência ficam para exercícios
seguintes. Não impor agora limite de 50 caracteres nem validação de domínio
Amazon/formato da URL. Faixa didática do número: inteiro maior que zero;
revisitar explicitamente se surgir necessidade de volumes especiais/zero.

Regras do exercício: rejeitar título e URL nulos, vazios ou compostos apenas
por espaços; rejeitar número menor que 1. Sinalizar entrada inválida com
IllegalArgumentException. Não exigir normalização de texto nesta etapa.

Casos de teste e resultados esperados:
- Dados válidos: objeto criado e métodos de leitura devolvem os dados recebidos.
- Título nulo/vazio/em branco: construção lança IllegalArgumentException.
- URL nula/vazia/em branco: construção lança IllegalArgumentException.
- Número zero/negativo: construção lança IllegalArgumentException.

Etapas: escrever a classe; escrever os testes acima; executar a suíte completa
com ./mvnw test na raiz da aplicação. O comando usa o Maven Wrapper para
compilar e executar os testes do projeto. Na revisão, executar a suíte antes
de concluir; se o ambiente impedir, registrar a limitação sem declarar sucesso.

Entrega: avisar quando os arquivos estiverem prontos para revisão, ou enviar
uma tentativa/dúvida específica. Oferecer ajuda com construtor, exceções e
JUnit conforme necessário; não bloquear a prática com nova entrevista geral.
Rubrica: funcionamento/regras 4; testes positivos e negativos 2; clareza 2;
explicação de por que validar no construtor 2. Não exigir explicação antes
de o aluno escrever sua primeira tentativa.

Estado: exercício atribuído, implementação e execução de testes pendentes.
Nesta interação apenas documentação alterada; nenhum teste executado.

### Revisão 02 — desafio 03 — 19/09/2026

O aluno corrigiu a condição numérica para aceitar apenas número maior que zero
e acrescentou getters. A classe compila. Foram encontrados quatro pontos ainda
pendentes: não existe VolumeTest; foram adicionados setters apesar do enunciado
pedir objeto sem setters; `strip()` devolve uma nova String e seu resultado não
foi utilizado; as expressões com `equals(null)` não protegem contra null e o uso
de `||` faz a validação aceitar entradas que deveriam ser rejeitadas. Uma String
nula causa NullPointerException antes da IllegalArgumentException esperada.

Orientação seguinte: validar null antes de invocar métodos da String e então
testar texto vazio ou composto apenas por espaços. Remover os setters para que
um Volume válido não possa ser invalidado após a construção. Criar
src/test/java/com/example/Manga_Monitor/dominio/VolumeTest.java com os casos
positivos e negativos já definidos. Não fornecer implementação completa sem
pedido; revisar a próxima tentativa do aluno.

Verificação executada com `./mvnw test`: BUILD SUCCESS, 1 teste executado,
0 falhas e 0 erros. Esse único teste é MangaMonitorApplicationTests e verifica
apenas a inicialização do contexto Spring; não testa Volume. O aviso futuro do
Mockito não interfere neste exercício. Nenhum código-fonte foi alterado nesta
revisão; somente a documentação de acompanhamento foi atualizada.

### Orientação de JUnit — desafio 03 — 19/09/2026

O aluno informou que ainda não sabe criar testes. Orientação planejada em
passos: criar VolumeTest no mesmo pacote lógico de Volume; importar Test,
assertEquals e assertThrows do JUnit Jupiter; começar com um cenário válido;
depois testar uma entrada inválida por vez. Explicar Arrange/Act/Assert e que
assertThrows recebe uma ação lambda para verificar a exceção.

Primeira prática orientada: escrever teste que cria Volume com título, número
e URL válidos e confere os três getters. Segunda prática: escrever teste para
número zero usando assertThrows. Após compreender esses dois formatos, ampliar
para número negativo e título/URL nulos, vazios e em branco. Executar toda a
suíte com `./mvnw test`, comando que compila e roda os testes pelo Maven Wrapper.
O agente fornece exemplos didáticos, mas o aluno digita a implementação.
Nenhum código-fonte ou teste alterado nesta interação.

### Revisão 03 — desafio 03 — 19/09/2026

O aluno removeu setters, corrigiu a regra do número, implementou getters e
criou seis testes de Volume: construção válida, título vazio, título nulo,
número negativo, URL vazia e URL nula. `./mvnw test` concluiu com BUILD SUCCESS:
7 testes no total, 0 falhas, 0 erros e 0 ignorados; seis pertencem a volumeTest
e um ao contexto Spring.

Os testes comprovam os cenários escritos, mas ainda não toda a especificação.
Pendências: o contrato do exercício pede IllegalArgumentException também para
título/URL nulos, enquanto classe e testes usam NullPointerException; faltam
título apenas com espaços, URL apenas com espaços e número zero. O import
org.mockito.internal.matchers.Null não é usado e acopla o teste a uma classe
interna do Mockito. Pela convenção Java, renomear classe e arquivo de
volumeTest para VolumeTest. `trim().equals("")` funciona para os casos atuais,
mas `isBlank()` expressa diretamente a regra de texto vazio ou só com espaços.
Os `else` após `throw` são desnecessários porque a execução já é interrompida.

Próxima entrega: uniformizar entradas inválidas como IllegalArgumentException,
acrescentar os três casos ausentes, remover o import não utilizado e aplicar o
nome VolumeTest. Depois executar novamente a suíte completa. Para concluir a
rubrica, aluno deve explicar brevemente por que validar no construtor impede
estados inválidos. Avaliação final pendente; nenhum código-fonte foi alterado
pelo agente, somente esta documentação.

### Decisão de ritmo — 19/09/2026

O aluno percebeu que a busca de acabamento no primeiro exercício estava
impedindo o avanço. Decisão: aceitar Volume como base funcional e mover os
ajustes restantes para dívida técnica não bloqueante. A suíte executada continua
como evidência: 7 testes, 0 falhas. Não exigir agora renomeação de volumeTest,
troca de exceção, simplificação dos condicionais ou casos adicionais.

Princípio para as próximas etapas: cada exercício deve entregar uma capacidade
nova da aplicação. Revisões devem bloquear avanço apenas quando houver erro que
impeça o próximo fluxo, perda de dados ou interpretação incorreta de requisito
central. Melhorias cosméticas e cobertura adicional serão agrupadas em uma
revisão posterior.

## Desafio 04 — registrar uma tentativa de consulta — 19/09/2026

Objetivo prático: representar no domínio o resultado de uma consulta de preço,
sem banco, HTTP ou Spring. Limite: até 250 linhas somando produção e testes.

Criar `Disponibilidade.java` no pacote dominio como enum com DISPONIVEL,
INDISPONIVEL e DESCONHECIDA. Criar `TentativaConsulta.java` no mesmo pacote com:
Volume volume; LocalDateTime instante; boolean sucesso; Disponibilidade
disponibilidade; BigDecimal preco; String motivoErro. Usar construtor e getters;
sem setters. BigDecimal representa dinheiro sem a aproximação de float/double.

Para manter o avanço, validar somente as três regras essenciais:
- volume, instante e disponibilidade são obrigatórios;
- sucesso permite preço presente ou ausente e exige motivoErro ausente;
- falha exige disponibilidade DESCONHECIDA, preço ausente e motivoErro presente.

Testes mínimos: consulta disponível com 39,90; consulta indisponível sem preço;
timeout com falha, disponibilidade desconhecida, preço ausente e motivo. Não
exigir combinações negativas exaustivas agora. Executar `./mvnw test` ao final.

Próximo fluxo após esta classe: criar um coletor simulado que devolva uma dessas
tentativas, permitindo demonstrar cadastro mais primeira consulta antes de
integrar HTTP real. Estado: desafio atribuído; aluno implementa. Nenhum código
alterado pelo agente nesta decisão, somente documentação.
