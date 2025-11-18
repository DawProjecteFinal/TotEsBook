
package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jmiro
 */
public interface PrestecRepository {
    
    void registrarPrestec(Prestec prestec);
    
    List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca);

    Optional<Prestec> findPrestecActiu(String isbn, Integer idUsuari);

    List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca);
    
    void updatePrestec(Prestec prestec);
    
    List<Prestec> findPrestecsActiusByUsuari(int idUsuari);

    
}
