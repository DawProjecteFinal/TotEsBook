package cat.totesbook.service.impl;

import cat.totesbook.domain.PropostaAdquisicio;
import cat.totesbook.repository.PropostaAdquisicioRepository;
import cat.totesbook.service.PropostaAdquisicioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author equip TotEsBook
 */
@Service
@Transactional

public class PropostaAdquisicioServiceImpl implements PropostaAdquisicioService {

    @Autowired
    private PropostaAdquisicioRepository repo;

    @Override
    public void guardarProposta(PropostaAdquisicio proposta) {
        repo.guardarProposta(proposta);
    }

    @Override
    public List<PropostaAdquisicio> findByUsuari(int idUsuari) {
        return repo.findByUsuari(idUsuari);
    }

    @Override
    public List<PropostaAdquisicio> findAllPropostes() {
        return repo.findAllPropostes();
    }

    @Override
    public PropostaAdquisicio findByIdProposta(int id) {
        return repo.findByIdProposta(id);
    }

    @Override
    public void eliminarProposta(int id) {
        repo.eliminarProposta(id);
    }

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
