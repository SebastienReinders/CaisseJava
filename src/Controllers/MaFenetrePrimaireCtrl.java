package Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.Console;
import java.util.Vector;

import GUI.*;
import autres.Magasin;
import autres.Ticket;
import autres.Article;

import autres.*;
import personnes.*;


public class MaFenetrePrimaireCtrl implements ActionListener {

    private MaFenetrePrimaire maFenetrePrimaire;
    private MaFenetrePrimaire view;
    public float QuEntree = 1;

    public void setMaFenetrePrimaire(MaFenetrePrimaire maFenetrePrimaire) {
        this.maFenetrePrimaire = maFenetrePrimaire;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // C'est ici que l'evenement se traite
        String command = e.getActionCommand();
        if ("openNewClientWindow".equals(command)) {
            SwingUtilities.invokeLater(NewClient::new);
        }
        if ("openNewArticleWindow".equals(command)) {
            SwingUtilities.invokeLater(NewArticle::new);
        }
        if ("openAboutWindow".equals(command)) {
            SwingUtilities.invokeLater(APropos::new);
        }
        if ("Options".equals(command))
        {
            SwingUtilities.invokeLater(() -> {
                PropertiesDialog dialog = new PropertiesDialog();
                dialog.setVisible(true);
            });
        }

        if("XenCours".equals(command))
        {
            JOptionPane.showMessageDialog(null,
                    "Il y a eu : " + Magasin.getInstance().getNombreDeVentes() + " ventes"
                            + "\nRéparties comme suit :\n"
                    + "Cartes de crédit : " + Magasin.getInstance().getPaiement1()
                    + "\n Cheques : " + Magasin.getInstance().getPaiement2()
                    + "\n Maestro : " + Magasin.getInstance().getPaiement3()
                    + "\n Cash : " + Magasin.getInstance().getPaiement4()
                    + "\n****************************************"
                    + "\n Pour un total de : " + (Magasin.getInstance().getPaiement1() + Magasin.getInstance().getPaiement2() + Magasin.getInstance().getPaiement3() + Magasin.getInstance().getPaiement4())
                    + "\n ATTENTION CECI n'est pas une cloture de caisse"
            );
        }

        if ("ZenCours".equals(command)){
            Magasin.getInstance().prochainZ();
            Magasin.getInstance().saveCloture();
            Magasin.getInstance().reinitialiserTicket();
            Magasin.getInstance().nullPaiement1();
            Magasin.getInstance().nullPaiement2();
            Magasin.getInstance().nullPaiement3();
            Magasin.getInstance().nullPaiement4();
            Magasin.getInstance().nullNumDeVentes();

        }
                //Gestion des paiements :
        if ("CarteCredit".equals(command))
        {
            //1. On prend le ticket vers le fichier
            Magasin.getInstance().getCurrentTicket().save();
            //2. On met à jour "ventes avant cloture de la caisse"
            Magasin.getInstance().setPaiement1(Magasin.getInstance().getCurrentTicket().getTotal());
            //3. On vide le ticket
            Magasin.getInstance().reinitialiserTicket();
            maFenetrePrimaire.mettreAJourTableauArticles();
            leTotalEst();
        }
        if ("Cheques".equals(command))
        {
            //1. On prend le ticket vers le fichier
            Magasin.getInstance().getCurrentTicket().save();
            //2. On met à jour "ventes avant cloture de la caisse"
            Magasin.getInstance().setPaiement2(Magasin.getInstance().getCurrentTicket().getTotal());
            //3. On vide le ticket
            Magasin.getInstance().reinitialiserTicket();
            maFenetrePrimaire.mettreAJourTableauArticles();
            leTotalEst();
        }
        if ("Maestro".equals(command))
        {
            //1. On prend le ticket vers le fichier
            Magasin.getInstance().getCurrentTicket().save();
            //2. On met à jour "ventes avant cloture de la caisse"
            Magasin.getInstance().setPaiement3(Magasin.getInstance().getCurrentTicket().getTotal());
            //3. On vide le ticket
            Magasin.getInstance().reinitialiserTicket();
            maFenetrePrimaire.mettreAJourTableauArticles();
            leTotalEst();
        }
        if ("Cash".equals(command))
        {
            //1. On prend le ticket vers le fichier
            Magasin.getInstance().getCurrentTicket().save();
            //2. On met à jour "ventes avant cloture de la caisse"
            Magasin.getInstance().setPaiement4(Magasin.getInstance().getCurrentTicket().getTotal());
            //3. On vide le ticket
            Magasin.getInstance().reinitialiserTicket();
            maFenetrePrimaire.mettreAJourTableauArticles();
            leTotalEst();
        }

        if ("ClickPress".equals(command)) {
            String textBoxValue = maFenetrePrimaire.getTextbox().getText();
            float quEnCours;
            if (!textBoxValue.isEmpty()) {
                quEnCours = Float.parseFloat(textBoxValue);
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
              //  System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
            } else {
                quEnCours = 1;
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            }
            // 2. vider la textbox
            maFenetrePrimaire.getTextbox().setText("");
            // 3. Ajouter dans le vecteur
            Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
            for (Article article : listeArticles) {
                if (article.getCodeBarre() == 54000001) {
                    Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                    ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                  //  System.out.println("Liste des articles du ticket en cours :");
                   /* for (Article article2 : Magasin.getInstance().getCurrentTicket().getArticleTickets()) {
                        System.out.println(article2);
                    }*/
                    maFenetrePrimaire.mettreAJourTableauArticles();
                    break;
                }
            }
            leTotalEst();
        }

        if ("ClickEaux".equals(command)) {
            String textBoxValue = maFenetrePrimaire.getTextbox().getText();
            float quEnCours;
            if (!textBoxValue.isEmpty()) {
                quEnCours = Float.parseFloat(textBoxValue);
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
             //   System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
            } else {
                quEnCours = 1;
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            }
            // 2. vider la textbox
            maFenetrePrimaire.getTextbox().setText("");
            // 3. Ajouter dans le vecteur
            Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
            for (Article article : listeArticles) {
                if (article.getCodeBarre() == 54000002) {
                    Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                    ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                 /*   System.out.println("Liste des articles du ticket en cours :");
                    for (Article article2 : Magasin.getInstance().getCurrentTicket().getArticleTickets()) {
                        System.out.println(article2);
                    }*/
                    maFenetrePrimaire.mettreAJourTableauArticles();
                    break;
                }
            }
            leTotalEst();
        }

        if ("ClickTabac".equals(command)) {
            String textBoxValue = maFenetrePrimaire.getTextbox().getText();
            float quEnCours;
            if (!textBoxValue.isEmpty()) {
                quEnCours = Float.parseFloat(textBoxValue);
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
              //  System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
            } else {
                quEnCours = 1;
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            }
            // 2. vider la textbox
            maFenetrePrimaire.getTextbox().setText("");
            // 3. Ajouter dans le vecteur
            Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
            for (Article article : listeArticles) {
                if (article.getCodeBarre() == 54000003) {
                    Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                    ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                 /*   System.out.println("Liste des articles du ticket en cours :");
                    for (Article article2 : Magasin.getInstance().getCurrentTicket().getArticleTickets()) {
                        System.out.println(article2);
                    }*/
                    maFenetrePrimaire.mettreAJourTableauArticles();
                    break;
                }
            }
            leTotalEst();
        }

        if ("ClickFruits".equals(command)) {
            String textBoxValue = maFenetrePrimaire.getTextbox().getText();
            float quEnCours;
            if (!textBoxValue.isEmpty()) {
                quEnCours = Float.parseFloat(textBoxValue);
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            //    System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
            } else {
                quEnCours = 1;
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            }
            // 2. vider la textbox
            maFenetrePrimaire.getTextbox().setText("");
            // 3. Ajouter dans le vecteur
            Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
            for (Article article : listeArticles) {
                if (article.getCodeBarre() == 54000004) {
                    Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                    ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                  /*  System.out.println("Liste des articles du ticket en cours :");
                    for (Article article2 : Magasin.getInstance().getCurrentTicket().getArticleTickets()) {
                        System.out.println(article2);
                    }*/
                    maFenetrePrimaire.mettreAJourTableauArticles();
                    break;
                }
            }
            leTotalEst();
        }

        if ("ClickLegumes".equals(command)) {
            String textBoxValue = maFenetrePrimaire.getTextbox().getText();
            float quEnCours;
            if (!textBoxValue.isEmpty()) {
                quEnCours = Float.parseFloat(textBoxValue);
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
             //   System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
            } else {
                quEnCours = 1;
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            }
            // 2. vider la textbox
            maFenetrePrimaire.getTextbox().setText("");
            // 3. Ajouter dans le vecteur
            Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
            for (Article article : listeArticles) {
                if (article.getCodeBarre() == 54000005) {
                    Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                    ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                   /* System.out.println("Liste des articles du ticket en cours :");
                    for (Article article2 : Magasin.getInstance().getCurrentTicket().getArticleTickets()) {
                        System.out.println(article2);
                    }*/
                    maFenetrePrimaire.mettreAJourTableauArticles();
                    break;
                }
            }
            leTotalEst();
        }
        if ("ClickAlcools".equals(command)) {
            String textBoxValue = maFenetrePrimaire.getTextbox().getText();
            float quEnCours;
            if (!textBoxValue.isEmpty()) {
                quEnCours = Float.parseFloat(textBoxValue);
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
              //  System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
            } else {
                quEnCours = 1;
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            }
            // 2. vider la textbox
            maFenetrePrimaire.getTextbox().setText("");
            // 3. Ajouter dans le vecteur
            Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
            for (Article article : listeArticles) {
                if (article.getCodeBarre() == 54000006) {
                    Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                    ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                 /*   System.out.println("Liste des articles du ticket en cours :");
                    for (Article article2 : Magasin.getInstance().getCurrentTicket().getArticleTickets()) {
                        System.out.println(article2);
                    }*/
                    maFenetrePrimaire.mettreAJourTableauArticles();
                    break;
                }
            }
            leTotalEst();
        }
        if ("ClickLotto".equals(command)) {
            String textBoxValue = maFenetrePrimaire.getTextbox().getText();
            float quEnCours;
            if (!textBoxValue.isEmpty()) {
                quEnCours = Float.parseFloat(textBoxValue);
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
              //  System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
            } else {
                quEnCours = 1;
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            }
            // 2. vider la textbox
            maFenetrePrimaire.getTextbox().setText("");
            // 3. Ajouter dans le vecteur
            Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
            for (Article article : listeArticles) {
                if (article.getCodeBarre() == 54000007) {
                    Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                    ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                  /*  System.out.println("Liste des articles du ticket en cours :");
                    for (Article article2 : Magasin.getInstance().getCurrentTicket().getArticleTickets()) {
                        System.out.println(article2);
                    }*/
                    maFenetrePrimaire.mettreAJourTableauArticles();
                    break;
                }
            }
            leTotalEst();
        }

        if ("ClickDivers0".equals(command)) {
            String textBoxValue = maFenetrePrimaire.getTextbox().getText();
            float quEnCours;
            if (!textBoxValue.isEmpty()) {
                quEnCours = Float.parseFloat(textBoxValue);
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            //    System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
            } else {
                quEnCours = 1;
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
            }
            // 2. vider la textbox
            maFenetrePrimaire.getTextbox().setText("");
            // 3. Ajouter dans le vecteur
            Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
            for (Article article : listeArticles) {
                if (article.getCodeBarre() == 54000008) {
                    Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                    ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                   /* System.out.println("Liste des articles du ticket en cours :");
                    for (Article article2 : Magasin.getInstance().getCurrentTicket().getArticleTickets()) {
                        System.out.println(article2);
                    }*/
                    maFenetrePrimaire.mettreAJourTableauArticles();
                    break;
                }
            }
            leTotalEst();
        }

        if ("ClickDivers6".equals(command)) {
            SwingUtilities.invokeLater(APropos::new);
        }

        if ("ClickPLU".equals(command)) {
            String text = maFenetrePrimaire.getTextbox().getText();
            // On va chercher le codebarre
       //     System.out.println("texte " + text + " Et t'es fier ?  :\n");
            long id = Long.valueOf(text);
          //  System.out.println("Numérique : " + id + "\n");
            // Est un article :
            if ((id > 10000000L && id < 99999999L) || (id > 3000000000000L && id < 9800000000000L)) {
              //  System.out.println("Est un article");
                Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
                for (Article article : listeArticles) {
              //      System.out.println(article);
                    if ((long) article.getCodeBarre() == (long) id) { // Utilise l'ID au lieu de 54000001
                        Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                        ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), ticketEnCours.getQuEnCours(), article.getPoints()));
                  //      System.out.println("J'ai trouvé " + id);
                        maFenetrePrimaire.mettreAJourTableauArticles();
                        break;
                    } else {
                   //     System.out.println("J'ai pas trouvé " + id);
                    }
                }
            }

            else // On est dans les clients
            {
                chercheClient(id);
                maFenetrePrimaire.getTextbox().setText("");
            }
            leTotalEst();
        }

            if ("majQu".equals(command)) {
                // 1. Copier la valeur de la textbox vers QuEnCours;
                String textBoxValue = maFenetrePrimaire.getTextbox().getText();
                float quEnCours = Float.parseFloat(textBoxValue);
                QuEntree = quEnCours;
                System.out.println(QuEntree + "****");
                Magasin.getInstance().getCurrentTicket().setQuEnCours(quEnCours);
                System.out.println("Valeur de QuEnCours : " + Magasin.getInstance().getCurrentTicket().getQuEnCours());
                // 2. vider la textbox
                maFenetrePrimaire.getTextbox().setText("");
                QuEntree = 1;
            }


        }


public  void leTotalEst(){
    String totalAsString = "TOTAL A PAYER :" + Magasin.getInstance().getCurrentTicket().getTotal();;
    maFenetrePrimaire.montantLabel.setText(totalAsString);
}
    public void appendToTextbox(String text) {
        System.out.println("Appending " + text + " to textbox"); // Impression de débogage
        String currentText = maFenetrePrimaire.getTextbox().getText();

        // Si la textbox est vide et que le texte à ajouter est ".", alors nous ajoutons "0." au lieu de "."
        if (currentText.isEmpty() && text.equals(".")) {
            text = "0.";
        }

        // Si le texte à ajouter est "." et que la textbox contient déjà un ".", alors nous ne faisons rien
        if (text.equals(".") && currentText.contains(".")) {
            return;
        }

        // Sinon, nous ajoutons le texte à la textbox
        maFenetrePrimaire.getTextbox().setText(currentText + text);
    }


    public void setupEnterKeyBinding() {
        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = maFenetrePrimaire.getTextbox().getText();
                // On va chercher le codebarre
                long id = Long.valueOf(text);

                      // Est un article :
                if ((id > 10000000L && id < 99999999L) || (id > 3000000000000L && id < 9800000000000L))
                {
                    System.out.println("Est un article");
                    Vector<Article> listeArticles = Magasin.getInstance().getListeArticles();
                    for (Article article : listeArticles) {
                        System.out.println(article);
                        if ((long)article.getCodeBarre() == (long)id) { // Utilise l'ID au lieu de 54000001
                            Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                            System.out.println(QuEntree + "******");
                            ticketEnCours.ajouterArticle(new Article(article.getCodeBarre(), article.getIntitule(), article.getPrix(), QuEntree, article.getPoints()));
                            System.out.println("J'ai trouvé " + id);
                            maFenetrePrimaire.mettreAJourTableauArticles();
                            break;
                            }
                        else {
                            System.out.println("J'ai pas trouvé " + id);
                        }


                        }
                    }

                else if (id > 9900000000000L && id < 9999999999999L)
                {
                    System.out.println("Est un bon de réduction");
                }
                else if (id > 9999999999999L)
                {
                    System.out.println("ERREUR");
                }
                else // On est dans les clients
                {
                    chercheClient(id);
                    maFenetrePrimaire.getTextbox().setText("");
                }


                // Réinitialisez le contenu de la textBox
                maFenetrePrimaire.getTextbox().setText("");
                System.out.println("**///****/////***////");
                leTotalEst();
            }
        };

        maFenetrePrimaire.getTextbox().addActionListener(enterAction);

    }

    void chercheClient(long id){

        Vector<Client> listeClient = Magasin.getInstance().getListeClients();
        for (Client client : listeClient) {
            System.out.println(client);
            if ((long) client.getNumCarte() == (long) id) {
                Ticket ticketEnCours = Magasin.getInstance().getCurrentTicket();
                System.out.println(QuEntree + "******");
                ticketEnCours.setClient(new Client(client.getNom(), client.getPrenom(), client.getDateNaissance(), client.getNumIntervenant(), client.getNumCarte(), client.getEmail(), client.getAdresse(), client.getPoints()));
                System.out.println("J'ai trouvé " + id);
                System.out.println(client.toString());
                // maFenetrePrimaire.mettreAJourTableauArticles();
                break;
            }
        }

    }
}
