package personnes;

import java.util.Date;
import java.util.Vector;

public abstract class IntervenantMagasin extends Personne {
    private int numIntervenant;

    public IntervenantMagasin() {
        super();
        this.numIntervenant = 0;
    }
    public IntervenantMagasin(String nom, String prenom, Date dateNaissance, int numIntervenant) {
        super(nom, prenom, dateNaissance);
        this.numIntervenant = numIntervenant;
    }

    public int getNumIntervenant() {
        return numIntervenant;
    }

    public void setNumIntervenant(int numIntervenant) {
        this.numIntervenant = numIntervenant;
    }

    @Override
    public String toString() {
        return super.toString() + ", numéro intervenant : " + numIntervenant;
    }

    public static void ajoutVecteurIntervenant(IntervenantMagasin intervenant, Vector<IntervenantMagasin> vecteur) {
        vecteur.add(intervenant);
    }

    public static void saveVecTXTIntervenant(Vector<IntervenantMagasin> intervenants, String filePath) {
        Vector<Personne> personnes = new Vector<>(intervenants);
        saveVecTXT(personnes, filePath);
    }

    public static Vector<IntervenantMagasin> loadVecTXTIntervenant(String filePath) {
        Vector<Personne> personnes = loadVecTXT(filePath);
        Vector<IntervenantMagasin> intervenants = new Vector<>();
        for (Personne personne : personnes) {
            if (personne instanceof IntervenantMagasin) {
                intervenants.add((IntervenantMagasin) personne);
            }
        }
        return intervenants;
    }

    public static void ajoutTXTIntervenant(IntervenantMagasin intervenant, String filePath) {
        ajoutTXT(intervenant, filePath);
    }
}
