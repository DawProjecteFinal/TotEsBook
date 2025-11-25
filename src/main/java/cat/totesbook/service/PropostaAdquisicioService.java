
package cat.totesbook.service;

import cat.totesbook.domain.PropostaAdquisicio;
import java.util.List;

/**
 *
 * @author equip TotEsBook
 */
public interface PropostaAdquisicioService {
    
    void guardarProposta(PropostaAdquisicio proposta);
    
    List<PropostaAdquisicio> findByUsuari(int idUsuari);

    List<PropostaAdquisicio> findAllPropostes();
    
    PropostaAdquisicio findByIdProposta(int id);
    
    void eliminarProposta(int id);
}
