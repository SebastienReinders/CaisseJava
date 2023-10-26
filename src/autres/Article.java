package autres;

import java.io.*;
import java.util.Vector;

public class Article {
    private long codeBarre;
    private String intitule;
    private float prix;
    private float qu;
    private int points;
    private float totalLigne;

    public Article(long codeBarre, String intitule, float prix, float qu, int points) {
        this.codeBarre = codeBarre;
        this.intitule = intitule;
        this.prix = prix;
        this.qu = qu;
        this.points = points;
        this.totalLigne = prix * (float) qu;
    }

    public long getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(long codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getQu() {
        return qu;
    }

    public void setQu(float qu) {
        this.qu = qu;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getTotalLigne() {
        return totalLigne;
    }

    public void setTotalLigne(int qu, float prix) {
        this.totalLigne = (float) qu * prix;
    }

    @Override
    public String toString() {
        return "Code barre : " + codeBarre + ", intitulé : " + intitule + ", prix : " + prix +
                ", qu : " + qu + ", points : " + points + ", Total ligne : " + totalLigne;
    }

    public String PrnLigneTicket() {
        if (qu == 1)
        {
            return   intitule + "\t" + totalLigne;
        }

        else if (qu > 1)
        {
            return "\t"+ qu + " * " + prix + "\n" + intitule + "\t" + totalLigne;
        }
        else
        {
            return "";
        }
    }

    public String PrnLigneJournal() {
        if (qu == 1)
        {
            return   codeBarre + "\t" + totalLigne;
        }

        else if (qu > 1)
        {
            return "\t"+ qu + " * " + prix + "\n" + codeBarre + "\t" + totalLigne;
        }
        else
        {
            return "";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Article)) {
            return false;
        }
        Article article = (Article) obj;
        return article.getCodeBarre() == this.codeBarre;
    }

    public static void ajoutVecteurArticle(Article article, Vector<Article> vecteur) {
        vecteur.add(article);
    }

    public static void saveVecTXTArticle(Vector<Article> articles, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Article article : articles) {
                String ligne = article.getCodeBarre() + "," + article.getIntitule() + "," + article.getPrix() + "," + article.getQu() + "," + article.getPoints();
                writer.write(ligne);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Article> loadVecTXTArticle(String filePath) {
        Vector<Article> articles = new Vector<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                long codeBarre = Long.parseLong(data[0]);
                String intitule = data[1];
                float prix = Float.parseFloat(data[2]);
                float qu = Float.parseFloat(data[3]);
                int points = Integer.parseInt(data[4]);
                Article article = new Article(codeBarre, intitule, prix, qu, points);
                System.out.println("Article créé : " + article);
                articles.add(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articles;
    }
    public static void ajoutTXTArticle(Article article, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {

            String ligne = article.getCodeBarre() + "," + article.getIntitule() + "," + article.getPrix() + "," + article.getQu() + "," + article.getPoints();
            writer.write(ligne);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
