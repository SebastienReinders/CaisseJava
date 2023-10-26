package autres;

import personnes.*;

import java.beans.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

import java.io.BufferedWriter;
import java.io.FileWriter;




public class Magasin {
    private static Magasin instance = null;
    private String nom;
    private String adresse;

    // Variables pour les clotures :
    private float paiement1 = 0; // Cartes de crédit
    private float paiement2 = 0; // cheques
    private float paiement3 = 0; // Maestro
    private float paiement4 = 0; // Cash

    private int nombreDeVentes = 0;
    private int z;

    private Vector<Client> listeClients;
    private Vector<Article> listeArticles;
    private Ticket ticketEnCours;

    private Magasin() {
        initialiserMagasin();
    }

    private void initialiserMagasin() {
// Charger les propriétés
        Caisse_Properties caisseProperties = new Caisse_Properties();
        caisseProperties.chargerProprietes();

        // Récupérer le nom et l'adresse du magasin à partir des propriétés
        setNom(caisseProperties.getNomMagasin());
        setAdresse(caisseProperties.getAdresseMagasin());

        // Lire le fichier clients et remplir le vecteur
        listeClients = Client.loadVecTXTClient("clients.txt");

        // Lire le fichier articles et remplir le vecteur
        listeArticles = Article.loadVecTXTArticle("articles.txt");
        System.out.println("Nombre d'articles chargés : " + listeArticles.size());

        creerTicket();
    }

    public void ajouterClient(Client client) {
        // 1.2 Si nouveau client, le rajouter dans le vecteur
        Client.ajoutVecteurClient(client, listeClients);
    }

    public void ajouterArticle(Article article) {
        // 2.2 Si nouvel article, le rajouter dans le vecteur
        Article.ajoutVecteurArticle(article, listeArticles);
    }

    public void creerTicket() {
        // 3.1 Créer un ticket

      //  Ticket ticketEnCours = null;
        try{
        ticketEnCours = (Ticket) Beans.instantiate(null, "autres.Ticket");
        }
        catch(ClassNotFoundException e) {
            System.out.println("Classe non trouvée...");
            System.exit(0);
        }
        catch (IOException e)
        {
            System.out.println("Erreur IO...");
            System.exit(0);
        }
    }

    public void setPaiement1 (float f){
        paiement1 += f;
        nombreDeVentes ++;
    }

    public void nullPaiement1(){
        paiement1 = 0;
    }
    public float getPaiement1() {
        return paiement1;
    }

    public void setPaiement2 (float f){
        paiement2 += f;
        nombreDeVentes ++;
    }
    public void nullPaiement2(){
        paiement2 = 0;
    }

    public float getPaiement2() {
        return paiement2;
    }

    public void setPaiement3 (float f){
        paiement3 += f;
        nombreDeVentes ++;
    }
    public void nullPaiement3(){
        paiement3 = 0;
    }

    public float getPaiement3() {
        return paiement3;
    }

    public void setPaiement4 (float f){
        paiement4 += f;
        nombreDeVentes ++;
    }

    public void nullPaiement4(){
        paiement4 = 0;
    }

    public float getPaiement4() {
        return paiement4;
    }
    public int getNombreDeVentes(){
        return nombreDeVentes;
    }
    public void nullNumDeVentes()
    {
        nombreDeVentes = 0;
    }

    public void imprimerTicket() {
        // 3.2 Mettre le ticket sur fichier (Imprimer le ticket)
        // Ici, vous devez écrire le code pour imprimer le ticket sur un fichier
    }
    public Vector<Article> getListeArticles() {
        listeArticles = Article.loadVecTXTArticle("articles.txt");
        return listeArticles;
    }

    public Vector<Client> getListeClients(){
        listeClients = Client.loadVecTXTClient("clients.txt");
        return listeClients;
    }
    public void reinitialiserTicket() {
        // 3.3 Réinitialiser le ticket
        ticketEnCours = new Ticket();
    }

    public void prochainZ()
    {
        z = LoadNumZ();
        SaveNumZ(z+1);
    }

    public static Magasin getInstance() {
        if (instance == null) {
            instance = new Magasin();
        }
        return instance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public Ticket getCurrentTicket() {
        return this.ticketEnCours;
    }

    @Override
    public String toString() {
        return "Magasin : " + nom + " (" + adresse + ")";
    }

    public void SaveNumZ(int i){
        try {
            PrintWriter out = new PrintWriter("NumZ.dat");
            out.println(i);
            out.close();
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture du fichier.");
            e.printStackTrace();
        }
    }
    public int LoadNumZ() {
        int num = 0;
        try {
            Scanner scanner = new Scanner(new File("NumZ.dat"));
            num = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
            return 1;
        }
        return num;
    }

    public void saveCloture(){
        String fileName = "clotures.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(paiement1 + " " + paiement2 + " " + paiement3 + " " + paiement4 + " " + nombreDeVentes + " " + z);
            writer.newLine();
            writer.close();
            System.out.println("Données de cloture enregistrées avec succès dans le fichier " + fileName);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement des données de cloture : " + e.getMessage());
        }
    }
}

