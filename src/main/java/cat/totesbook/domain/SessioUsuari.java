package cat.totesbook.domain;

/**
 *
 * @author edinsonioc
 */

// Aquest objecte és el que guardarem a la sessió de l'usuari.
// Unifica la informació de 'Usuaris' i 'Agents'.
public class SessioUsuari {

    private int id; // Pot ser id d'Usuari o id d'Agent
    private String nom;
    private String email;
    private Rol rol; // Fem servir el teu Rol.java enum!

    // Constructor per a Usuaris (lectors)
    public SessioUsuari(Usuari usuari) {
        this.id = usuari.getId();
        this.nom = usuari.getNom() + " " + usuari.getCognoms();
        this.email = usuari.getEmail();
        this.rol = Rol.USUARI; // El seu rol és ser un Usuari
    }

    // Constructor per a Agents (staff)
    public SessioUsuari(Agent agent) {
        this.id = agent.getIdAgent();
        this.nom = agent.getNom() + " " + agent.getCognoms();
        this.email = agent.getEmail();
        
        // Convertim el 'TipusAgent' a 'Rol'
        if (agent.getTipus() == Agent.TipusAgent.administrador) {
            this.rol = Rol.ADMIN;
        } else {
            this.rol = Rol.BIBLIOTECARI;
        }
    }

    // Getters
    public int getId() { 
        return id; 
    }
    
    public String getNomComplet() { 
        return nom; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public Rol getRol() { 
        return rol; 
    } // Aquest és el getter clau
}
