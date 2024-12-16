public class Enseignant {
    private int id;
    private String nom;
    private String prenom;
    private String specialite;

    // Constructeur par dÃ©faut
    public Enseignant() {
    }

    // Getter et Setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter et Setter pour prenom
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Getter et Setter pour specialite
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void modifier(String nouveauNom, String nouveauPrenom, String nouvelleSpecialite) {
        if (nouveauNom != null) {
            this.nom = nouveauNom;
        }
        if (nouveauPrenom != null) {
            this.prenom = nouveauPrenom;
        }
        if (nouvelleSpecialite != null) {
            this.specialite = nouvelleSpecialite;
        }
    }
}