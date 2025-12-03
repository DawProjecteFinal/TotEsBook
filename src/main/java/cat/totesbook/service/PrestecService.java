/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.service;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Reserva;
import cat.totesbook.dto.AutorEstadisticaDTO;
import cat.totesbook.dto.LlibreEstadisticaDTO;
import cat.totesbook.dto.UsuariEstadisticaDTO;
import java.util.List;


public interface PrestecService {

    void registrarPrestec(String isbn, String emailUsuari, int idAgentBibliotecari);

    List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca);

    void registrarDevolucio(String isbn, String emailUsuari, Integer idAgentBibliotecari);

    List<Prestec> findPrestecsActiusByUsuari(int idUsuari);
    
    List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca);
    
    // Mètodes per renovar préstec
    void renovarPrestec(Integer idPrestec);
    
    List<Prestec> findPrestecsRetornatsByUsuari(Integer idUsuari);
    // ---------
    
    // Mètode per registar la devolució d'un prèstec amb el botó
    void retornarPrestec(Integer idPrestec, Integer idAgentBibliotecari);
    
    // --- ==== SPRINT 3 (TEA 5) =====
    
    // Mètodes per treure estadístiques
    List<LlibreEstadisticaDTO> getEstadistiquesLlibres(String autor, String categoria);
    
    List<AutorEstadisticaDTO> getEstadistiquesAutors();
    
    List<UsuariEstadisticaDTO> getEstadistiquesUsuaris();
    
    // ----
    
    Prestec crearPrestecDesDeReserva(Reserva reserva, Agent agentBibliotecari);
}
