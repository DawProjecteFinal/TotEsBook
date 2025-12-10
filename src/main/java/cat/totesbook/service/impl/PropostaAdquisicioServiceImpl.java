package cat.totesbook.service.impl;

import cat.totesbook.domain.PropostaAdquisicio;
import cat.totesbook.repository.PropostaAdquisicioRepository;
import cat.totesbook.service.PropostaAdquisicioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe que implementa el servei de propostes d'adquisició.
 * 
 * @author Equip TotEsBook
 */
@Service
@Transactional

public class PropostaAdquisicioServiceImpl implements PropostaAdquisicioService {

    @Autowired
    private PropostaAdquisicioRepository repo;

    /**
     * Guadar les dades de la proposta.
     * 
     * @param proposta La proposta.
     */
    @Override
    public void guardarProposta(PropostaAdquisicio proposta) {
        repo.guardarProposta(proposta);
    }

    /**
     * Cercar propostes segons l'usuari que l'hagi fet.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb les propostes fetes per un usuari.
     */
    @Override
    public List<PropostaAdquisicio> findByUsuari(int idUsuari) {
        return repo.findByUsuari(idUsuari);
    }

    /**
     * Cerca totes les propostes fetes.
     * 
     * @return Una llista amb totes les propostes fetes.
     */
    @Override
    public List<PropostaAdquisicio> findAllPropostes() {
        return repo.findAllPropostes();
    }

    /**
     * Cercar proposta segons el seu ID.
     * 
     * @param id L'ID de la proposta.
     * @return Objecte PropostaAdquisició segons el seu ID.
     */
    @Override
    public PropostaAdquisicio findByIdProposta(int id) {
        return repo.findByIdProposta(id);
    }

    /**
     * Eliminar la proposta segons el seu ID.
     * 
     * @param id L'ID.
     */
    @Override
    public void eliminarProposta(int id) {
        repo.eliminarProposta(id);
    }

    /**
     * Actualitzar la informació de la proposta d'adquisició.
     * 
     * @param idProposta ID de la proposta.
     * @param estat L'estat de la proposta.
     * @param resposta La respota.
     */
    @Override
    public void actualitzarEstat(int idProposta, PropostaAdquisicio.EstatProposta estat, String resposta) {
        PropostaAdquisicio p = repo.findByIdProposta(idProposta);

        if (p != null) {
            p.setEstat(estat);
            p.setResposta(resposta);
            repo.actualitzar(p);
        }
    }

}
