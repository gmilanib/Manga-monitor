package com.example.Manga_Monitor.dominio;

import com.example.Manga_Monitor.infraestrutura.persistencia.DataHoraLegivelConverter;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TentativaConsultaPersistenciaTest {
    @Autowired EntityManager entityManager;
    @Autowired JdbcTemplate jdbc;

    @Test
    void deveGravarDataLegivelENomesDeTodosOsEnumsERelerSemPerdas() throws Exception {
        Volume volume = new Volume("Volume de teste", 1, "https://example.com");
        entityManager.persist(volume);
        LocalDateTime data = LocalDateTime.of(2026, 10, 23, 14, 30, 45, 123_000_000);
        for (Disponibilidade disponibilidade : Disponibilidade.values()) {
            // Usa o construtor JPA para testar persistência sem abrir o navegador.
            TentativaConsulta tentativa = new TentativaConsulta();
            definir(tentativa, "volume", volume);
            definir(tentativa, "data", data);
            definir(tentativa, "disponibilidade", disponibilidade);
            entityManager.persist(tentativa);
            entityManager.flush();
            Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil()
                    .getIdentifier(tentativa);
            assertEquals("2026-10-23 14:30:45.123", jdbc.queryForObject(
                    "select data from tentativa_consulta where id = ?", String.class, id));
            assertEquals(disponibilidade.name(), jdbc.queryForObject(
                    "select disponibilidade from tentativa_consulta where id = ?", String.class, id));
            entityManager.detach(tentativa);
            TentativaConsulta lida = entityManager.find(TentativaConsulta.class, id);
            assertEquals(data, ler(lida, "data"));
            assertEquals(disponibilidade, ler(lida, "disponibilidade"));
        }
    }

    @Test
    void devePreservarNulosERejeitarDatasInvalidas() {
        var converter = new DataHoraLegivelConverter();
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
        assertThrows(DateTimeParseException.class,
                () -> converter.convertToEntityAttribute("2026-02-30 12:00:00.000"));
        assertThrows(DateTimeParseException.class,
                () -> converter.convertToEntityAttribute("texto inválido"));
    }

    private static void definir(Object objeto, String campo, Object valor) throws Exception {
        var field = objeto.getClass().getDeclaredField(campo);
        field.setAccessible(true);
        field.set(objeto, valor);
    }

    private static Object ler(Object objeto, String campo) throws Exception {
        var field = objeto.getClass().getDeclaredField(campo);
        field.setAccessible(true);
        return field.get(objeto);
    }
}
