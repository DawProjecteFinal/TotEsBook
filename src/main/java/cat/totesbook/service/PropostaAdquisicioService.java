package cat.totesbook.service;

import cat.totesbook.domain.PropostaAdquisicio;
import java.util.List;

/**
 * Interfície  del servei de propostes d'adquisició.
 * 
 * @author Equip TotEsBook
 */
public interface PropostaAdquisicioService {
    
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
     * @param idProposta ID de la proposta.
     * @param estat L'estat de la proposta.
     * @param resposta La respota.
     */
    void actualitzarEstat(int idProposta,PropostaAdquisicio.EstatProposta estat,String resposta);
}
