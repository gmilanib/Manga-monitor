package com.example.Manga_Monitor.dominio;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class volumeTest {
    @Test
    public void deveCriarVolumeComDadosValidos() {
        Volume volume = new Volume(
                "Fullmetal Alchemist",
                19,
                "https://amazon.com.br/fma-19"
        );
        assertEquals("Fullmetal Alchemist", volume.getTitulo());
        assertEquals(19, volume.getNumero());
        assertEquals(
                "https://amazon.com.br/fma-19",
                volume.getURL()
        );
    }

    @Test
    public void develancarExcessaotitulo(){
        assertThrows(IllegalArgumentException.class, () -> new Volume("", 1, "URLTESTE.COM"));
            }
    @Test
    public void develancarExcessaotitulovazio(){
        assertThrows(NullPointerException.class, () -> new Volume(null , 1, "URLTESTE.com"));
    }
    @Test
    public void develancarExcessaoNumeroMenorQueZero(){
        assertThrows(IllegalArgumentException.class, () -> new Volume("TITULO TESTE", -1, "URLTESTE.com"));
    }
    @Test
    public void develancarExcessaoURLvazia(){
        assertThrows(IllegalArgumentException.class, () -> new Volume("TITULO TESTE", 1, ""));
    }
    @Test
    public void develancarExcessaoURLnula(){
        assertThrows(NullPointerException.class, () -> new Volume("TITULO TESTE" , 1, null));
    }

}


