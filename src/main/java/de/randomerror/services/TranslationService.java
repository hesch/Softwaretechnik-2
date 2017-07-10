package de.randomerror.services;

import de.randomerror.util.Provided;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Service used for Translating the Application
 */
@Provided
public class TranslationService {
    private Properties translations;

    public TranslationService() {
        setLanguage("en"); //set default Language
    }

    public void setLanguage(String name) {
        InputStream in = ClassLoader.getSystemResourceAsStream("translations/" + name + ".properties");
        translations = new Properties();
        try {
            translations.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String translate(String name) {
        return translations.getProperty(name, name);
    }
}
