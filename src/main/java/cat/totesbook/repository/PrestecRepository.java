/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import cat.totesbook.dto.AutorEstadisticaDTO;
import cat.totesbook.dto.LlibreEstadisticaDTO;
import cat.totesbook.dto.UsuariEstadisticaDTO;
import java.util.List;
import java.util.Optional;


public interface PrestecRepository {
    
    void registrarPrestec(Prestec prestec);
    
    List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca);

    Optional<Prestec> findPrestecActiu(String isbn, Integer idUsuari);

    List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca);
    
    void updatePrestec(Prestec prestec);
    
    List<Prestec> findPrestecsActiusByUsuari(int idUsuari);

    List<Prestec> findPrestecsRetornatsByUsuari(Integer idUsuari);
    
    // Mètodes per la lògica per renovar préstec
    Optional<Prestec> findById(Integer idPrestec);
    
    void save(Prestec prestec);
    // ----
    
    // SPRINT 3 (TEA 5): Mètodes per estadístiques
    List<LlibreEstadisticaDTO> findEstadistiquesLlibres(String autor, String categoria);
    List<AutorEstadisticaDTO> findEstadistiquesAutors();
    
    List<UsuariEstadisticaDTO> findEstadistiquesUsuaris();
    // ----
}
