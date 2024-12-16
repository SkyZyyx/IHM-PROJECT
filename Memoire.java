public class Memoire {
    private int id;
    private String title;
    private String author;
    private int year;
    private String summary;
    private String encadreur;

    // Constructeur par défaut
    public Memoire() {
    }

    // Getter et Setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter et Setter pour title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter et Setter pour author
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Getter et Setter pour year
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Getter et Setter pour summary
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // Getter et Setter pour encadreur
    public String getEncadreur() {
        return encadreur;
    }

    public void setEncadreur(String encadreur) {
        this.encadreur = encadreur;
    }

    // Méthode pour modifier les valeurs
    public void modifier(String nouveauTitle, String nouveauAuthor, Integer nouvelleYear,
                         String nouveauSummary, String nouveauEncadreur) {
        // Vérifier que les valeurs ne sont pas nulles avant de les mettre à jour
        if (nouveauTitle != null) {
            this.title = nouveauTitle;
        }
        if (nouveauAuthor != null) {
            this.author = nouveauAuthor;
        }
        if (nouvelleYear != null) {
            this.year = nouvelleYear;
        }
        if (nouveauSummary != null) {
            this.summary = nouveauSummary;
        }
        if (nouveauEncadreur != null) {
            this.encadreur = nouveauEncadreur;
        }
    }
}