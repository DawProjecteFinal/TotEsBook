package cat.totesbook.domain;

/**
 * Objecte que representa l'usuari loguejat a la sessió (HttpSession). Unifica
 * la informació provinent de les taules 'Usuaris' i 'Agents'.
 * 
 * @author Equip TotEsBook
 */
public class SessioUsuari {

    private int id; // Pot ser id d'Usuari o id d'Agent
    private String nomComplet; // Variable per al nom complet
    private String email;
    private Rol rol; // El teu Enum (ADMIN, BIBLIOTECARI, USUARI)

    private String bibliotecaNom;
    private Integer bibliotecaId;

    // Constructor BUIT privat per evitar que es creï sense dades
    private SessioUsuari() {
    }

    /**
     * Constructor per crear una SessioUsuari a partir d'un Usuari lector.
     *
     * @param usuari L'objecte Usuari obtingut de la BBDD. No pot ser nul.
     */
    public SessioUsuari(Usuari usuari) {
        if (usuari == null) {
            throw new IllegalArgumentException("L'objecte Usuari no pot ser nul per crear SessioUsuari.");
        }
        this.id = usuari.getId();
        // Construïm el nom complet, controlant possibles nuls
        this.nomComplet = (usuari.getNom() != null ? usuari.getNom() : "")
                + (usuari.getCognoms() != null ? " " + usuari.getCognoms() : "");
        this.email = usuari.getEmail();
        this.rol = Rol.USUARI; // Un Usuari sempre té aquest rol
    }

    /**
     * Constructor per crear una SessioUsuari a partir d'un Agent
     *
     * @param agent L'objecte Agent obtingut de la BBDD. No pot ser nul.
     */
    public SessioUsuari(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("L'objecte Agent no pot ser nul per crear SessioUsuari.");
        }
        this.id = agent.getIdAgent();
        // Construïm el nom complet, controlant possibles nuls
        this.nomComplet = (agent.getNom() != null ? agent.getNom() : "")
                + (agent.getCognoms() != null ? " " + agent.getCognoms() : "");
        this.email = agent.getEmail();

        // Convertim el TipusAgent a Rol
        if (agent.getTipus() == Agent.TipusAgent.administrador) {
            this.rol = Rol.ADMIN;
        } else { // Si no és admin, és bibliotecari
            this.rol = Rol.BIBLIOTECARI;
        }
        // Assignem només les dades necessàries de la biblioteca
        if (agent.getBiblioteca() != null) {
            this.bibliotecaNom = agent.getBiblioteca().getNom();
            this.bibliotecaId = agent.getBiblioteca().getIdBiblioteca();
        } else {
            // Assegurem que els valors siguin nuls si no hi ha biblioteca
            this.bibliotecaNom = null;
            this.bibliotecaId = null;
        }
    }

    // --- Getters ---
    /**
     * Retorna el id de l'usuari.
     * 
     * @return id de l'usuari.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna el nom complet de l'usuari.
     * 
     * @return nom complet de l'usuari.
     */
    public String getNomComplet() {
        return nomComplet;
    }

    /**
     * Retorna el correu electrònic de l'usuari.
     * 
     * @return correu electrònic de l'usuari.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retorna el id rol de l'usuari.
     * 
     * @return rol de l'usuari.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Retorna el nom de la biblioteca.
     * 
     * @return nom de la biblioteca.
     */
    public String getBibliotecaNom() {
        return bibliotecaNom;
    }

    /**
     * Retorna el id de la biblioteca.
     * 
     * @return id de la biblioteca.
     */
    public Integer getBibliotecaId() {
        return bibliotecaId;
    }

    /**
     * Assigna un nom de la biblioteca.
     * 
     * @param bibliotecaNom nom de la biblioteca.
     */
    public void setBibliotecaNom(String bibliotecaNom) {
        this.bibliotecaNom = bibliotecaNom;
    }

    /**
     * Assigna un id de la biblioteca.
     * 
     * @param bibliotecaId id de la biblioteca.
     */
    public void setBibliotecaId(Integer bibliotecaId) {
        this.bibliotecaId = bibliotecaId;
    }

    /**
     * Mètode per assignar una biblioteca després de la creació de l'objecte.
     * @param biblioteca biblioteca.
     */
    public void assignarBiblioteca(Biblioteca biblioteca) {
        this.bibliotecaNom = biblioteca.getNom();
        this.bibliotecaId = biblioteca.getIdBiblioteca();
    }
}
