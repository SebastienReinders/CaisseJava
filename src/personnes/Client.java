package personnes;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Client extends IntervenantMagasin {
    private long numCarte;
    private String email;
    private String adresse;
    private int points;

    public Client() {
        super();
        this.numCarte = 0;
        this.email = "";
        this.adresse = "";
        this.points = 0;
    }
    public Client(String nom, String prenom, Date dateNaissance, int numIntervenant, long numCarte, String email, String adresse, int points) {
        super(nom, prenom, dateNaissance, numIntervenant);
        this.numCarte = numCarte;
        this.email = email;
        this.adresse = adresse;
        this.points = points;
    }

    public long getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(long numCarte) {
        this.numCarte = numCarte;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Client : " + super.toString() + ", Numéro de carte : " + numCarte + ", Email : " + email + ", Adresse : " + adresse + ", Points : " + points;
    }

    public static void ajoutVecteurClient(Client client, Vector<Client> vecteur) {
        vecteur.add(client);
    }

    public static void saveVecTXTClient(Vector<Client> clients, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (Client client : clients) {
                String ligne = client.getNom() + "," + client.getPrenom() + "," + dateFormat.format(client.getDateNaissance()) + "," + client.getNumIntervenant() + "," + client.getNumCarte() + "," + client.getEmail() + "," + client.getAdresse() + "," + client.getPoints();
                writer.write(ligne);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Vector<Client> loadVecTXTClient(String filePath) {
        Vector<Client> clients = new Vector<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nom = data[0];
                String prenom = data[1];
                Date dateNaissance = dateFormat.parse(data[2]);
                int numIntervenant = Integer.parseInt(data[3]);
                long numCarte = Long.parseLong(data[4]);
                String email = data[5];
                String adresse = data[6];
                int points = Integer.parseInt(data[7]);

                Client client = new Client(nom, prenom, dateNaissance, numIntervenant, numCarte, email, adresse, points);
                clients.add(client);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public static void ajoutTXTClient(Client client, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ligne = client.getNom() + "," + client.getPrenom() + "," + dateFormat.format(client.getDateNaissance()) + "," + client.getNumIntervenant() + "," + client.getNumCarte() + "," + client.getEmail() + "," + client.getAdresse() + "," + client.getPoints();
            writer.write(ligne);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
