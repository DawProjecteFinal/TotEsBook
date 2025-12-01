package cat.totesbook.repository;

import cat.totesbook.domain.PropostaAdquisicio;
import java.util.List;

/**
 *
 * @author equip TotesBook
 */
public interface PropostaAdquisicioRepository {

    void guardarProposta(PropostaAdquisicio proposta);

    List<PropostaAdquisicio> findByUsuari(int idUsuari);

    List<PropostaAdquisicio> findAllPropostes();
    
    PropostaAdquisicio findByIdProposta(int id); 
    
    void eliminarProposta(int id);
    
    void actualitzar(PropostaAdquisicio p);
}
