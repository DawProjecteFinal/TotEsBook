/**
 *
 * @author edinsonioc
 */
package cat.totesbook.domain;

    /**
     * Objecte que representa l'usuari loguejat a la sessió (HttpSession).
     * Unifica la informació provinent de les taules 'Usuaris' i 'Agents'.
     */
    public class SessioUsuari {

        private int id; // Pot ser id d'Usuari o id d'Agent
        private String nomComplet; // Variable per al nom complet
        private String email;
        private Rol rol; // El teu Enum (ADMIN, BIBLIOTECARI, USUARI)

        // Constructor BUIT privat per evitar que es creï sense dades
        private SessioUsuari() {}

        /**
         * Constructor per crear una SessioUsuari a partir d'un Usuari (lector).
         * @param usuari L'objecte Usuari obtingut de la BBDD. No pot ser nul.
         */
        public SessioUsuari(Usuari usuari) {
            if (usuari == null) {
                 throw new IllegalArgumentException("L'objecte Usuari no pot ser nul per crear SessioUsuari.");
            }
            this.id = usuari.getId();
            // Construïm el nom complet, controlant possibles nuls
            this.nomComplet = (usuari.getNom() != null ? usuari.getNom() : "") +
                              (usuari.getCognoms() != null ? " " + usuari.getCognoms() : "");
            this.email = usuari.getEmail();
            this.rol = Rol.USUARI; // Un Usuari sempre té aquest rol
        }

        /**
         * Constructor per crear una SessioUsuari a partir d'un Agent (staff).
         * @param agent L'objecte Agent obtingut de la BBDD. No pot ser nul.
         */
        public SessioUsuari(Agent agent) {
             if (agent == null) {
                 throw new IllegalArgumentException("L'objecte Agent no pot ser nul per crear SessioUsuari.");
            }
            this.id = agent.getIdAgent();
             // Construïm el nom complet, controlant possibles nuls
            this.nomComplet = (agent.getNom() != null ? agent.getNom() : "") +
                              (agent.getCognoms() != null ? " " + agent.getCognoms() : "");
            this.email = agent.getEmail();

            // Convertim el TipusAgent a Rol
            if (agent.getTipus() == Agent.TipusAgent.administrador) {
                this.rol = Rol.ADMIN;
            } else { // Si no és admin, és bibliotecari
                this.rol = Rol.BIBLIOTECARI;
            }
        }

        // --- Getters ---
        public int getId() { return id; }
        public String getNomComplet() { return nomComplet; }
        public String getEmail() { return email; }
        public Rol getRol() { return rol; }
    }