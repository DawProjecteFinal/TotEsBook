package cat.totesbook.service;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import java.util.List;

/**
 *
 * @author jmiro
 */
public interface PrestecService {

    void registrarPrestec(String isbn, String emailUsuari, int idAgentBibliotecari);

    List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca);

    void registrarDevolucio(String isbn, String emailUsuari, Integer idAgentBibliotecari);

    List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca);
}
