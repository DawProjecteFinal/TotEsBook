package cat.totesbook.repository;

import cat.totesbook.domain.PropostaAdquisicio;
import java.util.List;

/**
 * Interfície per a les operacions de dades de la taula PropostaAdquisicio.
 * 
 * @author Equip TotesBook
 */
public interface PropostaAdquisicioRepository {

    /**
     * Guadar les dades de la proposta.
     * 
     * @param proposta La proposta.
     */
    void guardarProposta(PropostaAdquisicio proposta);

    /**
     * Cercar propostes segons l'usuari que l'hagi fet.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb les propostes fetes per un usuari.
     */
    List<PropostaAdquisicio> findByUsuari(int idUsuari);

    /**
     * Cerca totes les propostes fetes.
     * 
     * @return Una llista amb totes les propostes fetes.
     */
    List<PropostaAdquisicio> findAllPropostes();
    
    /**
     * Cercar proposta segons el seu ID.
     * 
     * @param id L'ID de la proposta.
     * @return Objecte PropostaAdquisició segons el seu ID.
     */
    PropostaAdquisicio findByIdProposta(int id); 
    
    /**
     * Eliminar la proposta segons el seu ID.
     * 
     * @param id L'ID.
     */
    void eliminarProposta(int id);
    
    /**
     * Actualitzar la informació de la proposta d'adquisició.
     * 
     * @param p La proposta d'adquisició.
     */
    void actualitzar(PropostaAdquisicio p);
}
