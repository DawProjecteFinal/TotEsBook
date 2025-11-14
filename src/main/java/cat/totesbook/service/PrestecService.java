
package cat.totesbook.service;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import java.util.List;


/**
 *
 * @author jmiro
 */
public interface PrestecService {
    // Modifiquem el mètode per acceptar IDs i gestionar tota la lògica de negoci
    void registrarPrestec(String isbn, String emailUsuari, int idAgent);
    
    List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca);
}
