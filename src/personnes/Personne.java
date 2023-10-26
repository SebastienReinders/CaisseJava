package personnes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Personne {
    private String nom;
    private String prenom;
    private Date dateNaissance;

    public Personne() {
        this.nom = "";
        this.prenom = "";
        this.dateNaissance = null;
    }
    public Personne(String nom, String prenom, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Personne [nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + "]";
    }

    // Méthode equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Personne other = (Personne) obj;
        if (dateNaissance == null) {
            if (other.dateNaissance != null)
                return false;
        } else if (!dateNaissance.equals(other.dateNaissance))
            return false;
        if (nom == null) {
            if (other.nom != null)
                return false;
        } else if (!nom.equals(other.nom))
            return false;
        if (prenom == null) {
            if (other.prenom != null)
                return false;
        } else if (!prenom.equals(other.prenom))
            return false;
        return true;
    }

    public static void ajoutVecteur(Personne personne, Vector<Personne> vecteur) {
        vecteur.add(personne);
    }

    public static void saveVecTXT(Vector<Personne> personnes, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (Personne personne : personnes) {
                String ligne = personne.getNom() + "," + personne.getPrenom() + "," + dateFormat.format(personne.getDateNaissance());
                writer.write(ligne);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Personne> loadVecTXT(String filePath) {
        Vector<Personne> personnes = new Vector<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nom = data[0];
                String prenom = data[1];
                Date dateNaissance = dateFormat.parse(data[2]);

                Personne personne = new Personne(nom, prenom, dateNaissance);
                personnes.add(personne);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return personnes;
    }

    public static void ajoutTXT(Personne personne, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ligne = personne.getNom() + "," + personne.getPrenom() + "," + dateFormat.format(personne.getDateNaissance());
            writer.write(ligne);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
