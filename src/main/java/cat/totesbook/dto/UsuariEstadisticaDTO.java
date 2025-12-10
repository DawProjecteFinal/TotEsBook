package cat.totesbook.dto;

import cat.totesbook.domain.Usuari;

/**
 * DTO que representa les estadístiques d'un usuari.
 * 
 * @author Equip TotEsBook
 */
public class UsuariEstadisticaDTO {
    private Usuari usuari;
    private Long totalPrestecs;

    /**
     * Creació d'un objecte amb l'usari i el total de préstecs.
     * 
     * @param usuari L'usuari.
     * @param totalPrestecs El total de préstecs.
     */
    public UsuariEstadisticaDTO(Usuari usuari, Long totalPrestecs) {
        this.usuari = usuari;
        this.totalPrestecs = totalPrestecs;
    }

    /**
     * Retorna el usuari.
     * 
     * @return El usuari.
     */
    public Usuari getUsuari() { 
        return usuari; 
    }
    
    /**
     * Retorna el total de préstecs.
     * 
     * @return El total de préstecs.
     */
    public Long getTotalPrestecs() { 
        return totalPrestecs; 
    }
}
