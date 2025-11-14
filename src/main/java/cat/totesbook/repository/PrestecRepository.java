
package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import java.util.List;

/**
 *
 * @author jmiro
 */
public interface PrestecRepository {
    
    void registrarPrestec(Prestec prestec);
    
    List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca);
}
