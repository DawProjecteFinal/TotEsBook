package cat.totesbook.dto;

import cat.totesbook.domain.Usuari;
/**
 *
 * @author edinsonioc
 */
public class UsuariEstadisticaDTO {
    private Usuari usuari;
    private Long totalPrestecs;

    public UsuariEstadisticaDTO(Usuari usuari, Long totalPrestecs) {
        this.usuari = usuari;
        this.totalPrestecs = totalPrestecs;
    }

    public Usuari getUsuari() { 
        return usuari; 
    }
    
    public Long getTotalPrestecs() { 
        return totalPrestecs; 
    }
}
