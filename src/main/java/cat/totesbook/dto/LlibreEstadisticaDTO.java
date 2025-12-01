package cat.totesbook.dto;

import cat.totesbook.domain.Llibre;

/**
 *
 * @author edinsonioc
 */
public class LlibreEstadisticaDTO {
    private Llibre llibre;
    private Long totalPrestecs;

    public LlibreEstadisticaDTO(Llibre llibre, Long totalPrestecs) {
        this.llibre = llibre;
        this.totalPrestecs = totalPrestecs;
    }

    public Llibre getLlibre() { 
        return llibre; 
    }
    
    public Long getTotalPrestecs() { 
        return totalPrestecs; 
    }
}
