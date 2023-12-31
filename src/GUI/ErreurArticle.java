package GUI;

import javax.swing.*;
import java.awt.*;

public class ErreurArticle extends JFrame {

    public ErreurArticle() {
        setTitle("Erreur");
        setSize(350, 175);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Label "Sebarby Caisse V1.406 Demo"
        JLabel titleLabel = new JLabel("Erreur");
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setVerticalAlignment(SwingConstants.TOP);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Label "Ceci est une version de démonstration..."
        JLabel descriptionLabel = new JLabel("Cet article n'existe pas ! ou format CB incorrect");
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setVerticalAlignment(SwingConstants.CENTER);
        mainPanel.add(descriptionLabel, BorderLayout.CENTER);

        // Bouton "OK"
        JButton okButton = new JButton("OK");
        //On ne va pas s'amuser à jouer au MVC pour si peu.
        okButton.addActionListener(e -> {
            dispose(); // Ferme la fenêtre
        });
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(okButton);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Ajout du Layout principal à la fenêtre
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ErreurArticle());
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }
}
