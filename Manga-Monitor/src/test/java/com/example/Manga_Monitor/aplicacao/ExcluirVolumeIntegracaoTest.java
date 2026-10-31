package com.example.Manga_Monitor.aplicacao;

import com.example.Manga_Monitor.dominio.Volume;
import com.example.Manga_Monitor.repository.VolumeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ExcluirVolumeIntegracaoTest {
    @Autowired ExcluirVolume servico;
    @Autowired VolumeRepository volumes;
    @Autowired JdbcTemplate jdbc;

    @BeforeEach
    @AfterEach
    void limparBancoDeTeste() {
        jdbc.execute("DROP TRIGGER IF EXISTS impedir_exclusao");
        jdbc.update("DELETE FROM tentativa_consulta");
        jdbc.update("DELETE FROM volume");
        jdbc.execute("PRAGMA foreign_keys = ON");
    }

    private Volume cadastrar(String titulo, int numero) {
        Volume volume = volumes.saveAndFlush(new Volume(titulo, numero, "https://loja/exemplo"));
        assertThat(volume.getId()).isPositive();
        return volume;
    }

    private void historico(Volume volume) {
        // Fixture direta evita iniciar Playwright e acessar a internet.
        jdbc.update("INSERT INTO tentativa_consulta (volume_id, data, disponibilidade, valor) "
                + "VALUES (?, '2026-10-24 12:00:00.000', 'DISPONIVEL', 25.90)", volume.getId());
    }

    @Test
    void buscaParcialDeveIgnorarCaixaEFiltrarNumeroMantendoDuplicados() {
        Volume primeiro = cadastrar("Naruto", 3);
        Volume segundo = cadastrar("Naruto Especial", 3);
        cadastrar("Naruto", 4);
        cadastrar("One Piece", 3);
        assertThat(servico.buscar("  nARu  ", 3)).extracting(Volume::getId)
                .containsExactly(primeiro.getId(), segundo.getId());
    }

    @Test
    void caracteresPercentualESublinhadoDevemSerLiterais() {
        cadastrar("Naruto", 3);
        Volume especial = cadastrar("Oferta 100%_Mangá", 3);
        assertThat(servico.buscar("%_", 3)).extracting(Volume::getId).containsExactly(especial.getId());
    }

    @Test
    void exclusaoDeveRemoverSomenteVolumeSelecionadoESeuHistorico() {
        Volume escolhido = cadastrar("Naruto", 3);
        Volume outro = cadastrar("Naruto", 3);
        historico(escolhido);
        historico(escolhido);
        historico(outro);
        assertThat(servico.excluir(escolhido.getId())).isTrue();
        assertThat(volumes.findById(escolhido.getId())).isEmpty();
        assertThat(volumes.findById(outro.getId())).isPresent();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM tentativa_consulta WHERE volume_id = ?",
                Integer.class, escolhido.getId())).isZero();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM tentativa_consulta WHERE volume_id = ?",
                Integer.class, outro.getId())).isEqualTo(1);
    }

    @Test
    void volumeSemHistoricoTambemPodeSerExcluido() {
        Volume volume = cadastrar("Naruto", 3);
        assertThat(servico.excluir(volume.getId())).isTrue();
        assertThat(volumes.findById(volume.getId())).isEmpty();
    }

    @Test
    void falhaAoExcluirVolumeDeveRestaurarHistoricoNaMesmaTransacao() {
        Volume volume = cadastrar("Naruto", 3);
        historico(volume);
        jdbc.execute("CREATE TRIGGER impedir_exclusao BEFORE DELETE ON volume "
                + "BEGIN SELECT RAISE(ABORT, 'falha simulada'); END");
        assertThatThrownBy(() -> servico.excluir(volume.getId())).isInstanceOf(RuntimeException.class);
        assertThat(volumes.findById(volume.getId())).isPresent();
        assertThat(jdbc.queryForObject("SELECT count(*) FROM tentativa_consulta WHERE volume_id = ?",
                Integer.class, volume.getId())).isEqualTo(1);
    }

    @Test
    void cadastroInexistenteNaoDeveRemoverOutros() {
        Volume volume = cadastrar("Naruto", 3);
        assertThat(servico.excluir(Long.MAX_VALUE)).isFalse();
        assertThat(volumes.findById(volume.getId())).isPresent();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void tituloAusenteDeveSerRejeitado(String titulo) {
        assertThatIllegalArgumentException().isThrownBy(() -> servico.buscar(titulo, 3));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void numeroNaoPositivoDeveSerRejeitado(int numero) {
        assertThatIllegalArgumentException().isThrownBy(() -> servico.buscar("Naruto", numero));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void identificadorInvalidoDeveSerRejeitado(long id) {
        assertThatIllegalArgumentException().isThrownBy(() -> servico.excluir(id));
    }
}
