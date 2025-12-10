package cat.totesbook.dto;

import cat.totesbook.domain.Llibre;

/**
 * DTO que representa les estadístiques d'un llibre.
 * 
 * @author Equip TotEsBook
 */
public class LlibreEstadisticaDTO {
    private Llibre llibre;
    private Long totalPrestecs;

    /**
     * Creació d'un objecte amb el llibre i el total de préstecs.
     * 
     * @param llibre El llibre.
     * @param totalPrestecs El total de préstecs.
     */
    public LlibreEstadisticaDTO(Llibre llibre, Long totalPrestecs) {
        this.llibre = llibre;
        this.totalPrestecs = totalPrestecs;
    }

    /**
     * Retorna el llibre.
     * 
     * @return El llibre.
     */
    public Llibre getLlibre() { 
        return llibre; 
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
