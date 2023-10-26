package autres;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Caisse_Properties {
    private Properties properties;

    public Caisse_Properties() {
        properties = new Properties();
    }

    public void chargerProprietes() {
        try {
            FileInputStream input = new FileInputStream("caisse.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNomMagasin() {
        return properties.getProperty("nomMagasin");
    }

    public void setNomMagasin(String nomMagasin) {
        properties.setProperty("nomMagasin", nomMagasin);
        sauvegarderProprietes();
    }

    public String getAdresseMagasin() {
        return properties.getProperty("adresseMagasin");
    }

    public void setAdresseMagasin(String adresseMagasin) {
        properties.setProperty("adresseMagasin", adresseMagasin);
        sauvegarderProprietes();
    }

    private void sauvegarderProprietes() {
        try {
            FileOutputStream output = new FileOutputStream("caisse.properties");
            properties.store(output, "Caisse Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
