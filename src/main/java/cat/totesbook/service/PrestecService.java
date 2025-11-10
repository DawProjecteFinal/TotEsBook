
package cat.totesbook.service;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Usuari;

/**
 *
 * @author jmiro
 */
public interface PrestecService {
    void registrarPrestec(Usuari usuari, Llibre llibre, Biblioteca biblioteca, Agent agent);
}
