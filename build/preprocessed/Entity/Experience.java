package Entity;


public class Experience {

    private int id;  
    private String titre;
    private String contenu;
    private String pays;
    private String type;
    private String climat;
    private double depense;

    public Experience(int id, String titre, String contenu, String pays, String type, String climat, double depense) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.pays = pays;
        this.type = type;
        this.climat = climat;
        this.depense = depense;
    }

    
    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClimat() {
        return climat;
    }

    public void setClimat(String climat) {
        this.climat = climat;
    }

//    public double getDepense() {
//        return depense;
//    }
    
    public String getDepense() {
        return String.valueOf(depense);
    }

    public void setDepense(String depense) {
        this.depense = Double.parseDouble(depense);
    }

    public String toString() {
        return "Experience{" + "id=" + id + ", titre=" + titre + ", contenu=" + contenu + ", pays=" + pays + ", type=" + type + ", climat=" + climat + ", depense=" + depense + '}';
    }

    public Experience() {
    }
    


    Experience getExperience() {
        return null;
    }
}
