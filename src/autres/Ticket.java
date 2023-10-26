package autres;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

        // Pq pas javax.swing.* ????

import personnes.*;


public class Ticket {
    private Date dateTicket;
    private int numTicket;
    private Client client;
    private Vector<Article> articles;
    private float sousTotal;
    private float reduction;
    private float total;
    private int pointsTicket;
    private float quEnCours;
    private int clientPresent = 0;
    private  int redPoints = 0;

    private PropertyChangeSupport propertyChangeSupport;

    public Ticket() {
      //  SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        dateTicket = new Date();
        majNumTicket();
        sousTotal = 0;
        reduction = 0;
        total = 0;
        pointsTicket = 0;
        articles = new Vector<>();
        client = new Client();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public Date getDateTicket() {
        return dateTicket;
    }

    public void setDateTicket(Date dateTicket) {
        this.dateTicket = dateTicket;
    }

    public int getNumTicket() {
        return numTicket;
    }

    public Vector<Article> getArticles() {
        return articles;
    }
    public void setNumTicket(int numTicket) {
        this.numTicket = numTicket;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        Client oldClient = this.client;
        clientPresent = 1;
        this.client = client;
        propertyChangeSupport.firePropertyChange("client", oldClient, client);
    }



    public void setArticles(Vector<Article> articles) {
        Vector<Article> oldArticles = this.articles;
        this.articles = articles;
        propertyChangeSupport.firePropertyChange("articles", oldArticles, articles);
    }
    public void mettreAJourTableauArticles(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        for (Article article : articles) {
            Object[] rowData = { article.getIntitule(), article.getQu(), article.getPrix(), article.getTotalLigne() };
            tableModel.addRow(rowData);
        }
    }
    public void ajouterArticle(Article article) {
        articles.add(article);
        propertyChangeSupport.firePropertyChange("articles", null, articles);
    }

    public float getSousTotal() {
        return sousTotal;
    }

    public float getReduction() {
        return reduction;
    }

    public float getTotal() {
        calculTotal();
        return total;
    }

    public int getPointsTicket() {
        return pointsTicket;
    }

    public float getQuEnCours() {
        return quEnCours;
    }

    public Vector<Article> getArticleTickets() {
        return articles;
    }

    public void setQuEnCours(float quEnCours) {
        float oldQuEnCours = this.quEnCours;
        this.quEnCours = quEnCours;
        propertyChangeSupport.firePropertyChange("quEnCours", oldQuEnCours, quEnCours);
    }

    public void calculSousTotal() {
        sousTotal = 0;
        for (Article article : articles) {
            sousTotal += article.getQu() * article.getPrix();
        }
    }

    public void calculReduction() {
        calculSousTotal();

        if (sousTotal >= 250) {
            reduction = sousTotal * 0.1f;
            System.out.println("On a une reduction de : " + reduction);
        }
        if (clientPresent == 1 && redPoints == 0 && client.getPoints() >= 500)
        {
            System.out.println("Le client a : " + client.getPoints() + " points");
            client.setPoints(client.getPoints()-500);
            System.out.println("Le client a eu 5 eur donc reduction = " + reduction + " et il lui reste" + client.getPoints());
            reduction = reduction + 5;
            redPoints = 1;
        }


    }

    public void calculTotal() {
        calculSousTotal();
        calculReduction();


        total = sousTotal - reduction;
        calculPointsTicket();
    }

    public void calculPointsTicket() {
        // Calcul du nombre de points générés par les articles achetés
        int pointsArticles = client.getPoints();
        for (Article article : articles) {
            pointsArticles += article.getQu() * article.getPoints();
        }
        // Calcul du nombre total de points du ticket
        pointsTicket = (int) total + pointsArticles;

        Vector<Client> clients = Client.loadVecTXTClient("clients.txt");
        for (Client c : clients) {
            if (c.getNumCarte() == client.getNumCarte()) {
                c.setPoints(pointsTicket);
                break;
            }
        }
        Client.saveVecTXTClient(clients, "clients.txt");
    }

    private void majNumTicket(){
        numTicket = LoadNumTicket();
        SaveNumticket(numTicket+1);
    }

    @Override
    public String toString() {
        // Méthode toString() modifiée pour inclure les articles du ticket
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket n°").append(numTicket).append("\n");
        sb.append("Date : ").append(dateTicket).append("\n\n");
        sb.append("Articles : \n");
        for (Article article : articles) {
            sb.append("\t").append(article).append("\n");
        }
        sb.append("\n");
        if (client != null) {
            sb.append("Client : ").append(client).append("\n");
        }
        sb.append("Sous-total : ").append(sousTotal).append("€\n");
        if (reduction > 0) {
            sb.append("Réduction : ").append(reduction).append("€\n");
        }
        sb.append("Total : ").append(total).append("€\n");
        sb.append("Points gagnés : ").append(pointsTicket).append("\n");
        return sb.toString();
    }

    public void save() {
        // Formatter pour le nom du fichier


        String fileName = "Tickets/T" + numTicket + ".ser";

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(this.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture du fichier.");
            e.printStackTrace();
        }
    }
    public static String load(String fileName) {
        StringBuilder ticketStr = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                ticketStr.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de la lecture du fichier.");
            e.printStackTrace();
        }

        return ticketStr.toString();
    }

    private void SaveNumticket(int i){
        try {
            PrintWriter out = new PrintWriter("Numticket.dat");
            out.println(i);
            out.close();
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'écriture du fichier.");
            e.printStackTrace();
        }
    }
    private int LoadNumTicket() {
        int num = 0;
        try {
            Scanner scanner = new Scanner(new File("Numticket.dat"));
            num = scanner.nextInt();
            scanner.close();
        } catch (FileNotFoundException e) {
          return 1;
        }
        return num;
    }

}
