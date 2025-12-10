
package cat.totesbook.repository.impl;

import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Prestec.EstatPrestec;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author equip TotEsBook
 */

// Per a fer els tests utilitzem Mockito que és una biblioteca per crear 
// objectes falsos (mocks), comportaments simulats (stubs) i podem fer verificacions.
class PrestecDAOTest {

    @Test
    void testFindPrestecsActiusByUsuari() {

        // Mocks necessaris
        EntityManager em = mock(EntityManager.class);
        TypedQuery<Prestec> query = mock(TypedQuery.class);

        // Crear objecte prestec real amb EM simulat
        PrestecDAO dao = new PrestecDAO();
        dao.setEntityManager(em);

        // Preparem dades de prova. Creem uns prestecs que "retornarà la BD"
        Prestec p1 = new Prestec();
        Prestec p2 = new Prestec();
        List<Prestec> resultatsSimulats = List.of(p1, p2);

        // Comportament simulat
        when(em.createQuery(anyString(), eq(Prestec.class))).thenReturn(query);
        when(query.setParameter(eq("idUsuari"), anyInt())).thenReturn(query);
        when(query.setParameter(eq("estat"), eq(EstatPrestec.actiu))).thenReturn(query);
        when(query.getResultList()).thenReturn(resultatsSimulats);

        // Executar mètode real
        List<Prestec> resultat = dao.findPrestecsActiusByUsuari(10);

        // Validacions
        assertEquals(2, resultat.size());
        assertSame(p1, resultat.get(0));
        assertSame(p2, resultat.get(1));

        // Verifiquem que s'han cridat les parts importants
        verify(em).createQuery(anyString(), eq(Prestec.class));
        verify(query).setParameter("idUsuari", 10);
        verify(query).setParameter("estat", EstatPrestec.actiu);
        verify(query).getResultList();
    }
}
