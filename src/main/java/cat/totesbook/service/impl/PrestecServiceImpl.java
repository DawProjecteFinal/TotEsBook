package cat.totesbook.service.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.PrestecRepository;
import cat.totesbook.service.PrestecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jmiro
 */
@Service
public class PrestecServiceImpl implements PrestecService{

    @Autowired
    private PrestecRepository prestecRepository;

    public void registrarPrestec(Usuari usuari, Llibre llibre, Biblioteca biblioteca, Agent agent) {
        Prestec prestec = new Prestec(usuari, llibre, biblioteca, agent);
        prestecRepository.guardarPrestec(prestec);
    }
}
