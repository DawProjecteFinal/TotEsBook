package cat.totesbook.repository.impl;

import cat.totesbook.domain.Llibre;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author equip TotEsBook
 */
// Per a fer els tests utilitzem Mockito que és una biblioteca per crear 
// objectes falsos (mocks), comportaments simulats (stubs) i podem fer verificacions.
public class LlibreDAOTest {

    /**
     * Metode per provar el metode de la classe LlibreDAO getLlibreByIsbn() que
     * busca un llibre pel seu ISBN
     */
    @Test
    void testGetLlibreByIsbn_found() {
        // Creem un EntityManager falç per a fer els testos (mock)
        EntityManager em = mock(EntityManager.class);

        // Instanciar repositori i injectar el mock (EntityManager falç)
        LlibreDAO dao = new LlibreDAO();
        dao.setEntityManager(em);

        // Crear llibre de prova
        Llibre llibre = new Llibre();
        llibre.setIsbn("12345");
        llibre.setTitol("Prova Llibre");

        // Amb When() estem definint el comportament;
        // Li diem a Mockito que quan el cridin amb findt() i li passin el paràmetre
        // "12345" retorni objecte Llibre creat anteriorment. 
        when(em.find(Llibre.class, "12345")).thenReturn(llibre);

        // Executar el mètode a provar
        Optional<Llibre> resultat = dao.getLlibreByIsbn("12345");

        // Comprovacions
        assertTrue(resultat.isPresent());   // Comprovem que l'Optional no sigui buit
        assertEquals("Prova Llibre", resultat.get().getTitol());    // comprovem que coincideix el títol

        // Comprovar que es crida correctament el Entity Manager
        verify(em).find(Llibre.class, "12345");
    }

    /**
     * Comprovem en aquest cas quan no es troba un ISBN
     */
    @Test
    void testGetLlibreByIsbn_notFound() {
        // Mock EntityManager
        EntityManager em = mock(EntityManager.class);

        // Creem un Llibre buit
        LlibreDAO llibre = new LlibreDAO();
        llibre.setEntityManager(em);

        // Retornar null per ISBN inexistent
        when(em.find(Llibre.class, "00000")).thenReturn(null);

        // Executar el mètode a provar
        Optional<Llibre> resultat = llibre.getLlibreByIsbn("00000");

        // Com el Optional estarà buit perque el objecte està buit, elñ assertFalse donarà correcte
        assertFalse(resultat.isPresent());
        verify(em).find(Llibre.class, "00000");
    }
}
