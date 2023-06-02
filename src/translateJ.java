import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class translateJ {
    private Map<String, Map<String, String>> translations;
    private String SEP;
    private String FAIL;
    
    public translateJ() {
        this("KEY_SEP", "COULD NOT TRANSLATE: ");
    }

    public translateJ(String SEPERATOR, String FAILED) { //user-defined sep and failure strings
        translations = new HashMap<>();
        this.SEP = SEPERATOR;
        this.FAIL = FAILED;
    }

    public void readTranslationFile(String lang, String file) throws IOException {
        Map<String, String> translationMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SEP, 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    translationMap.put(key, value);
                }
            }
        }
        translations.put(lang, translationMap);
    }

    public String translate(String key, String lang) {
        Map<String, String> translationMap = translations.get(lang);
        if (translationMap != null) {
            String translation = translationMap.get(key);
            if (translation != null) {
                return translation;
            }
        }
        return FAIL + key;
    }

    //Use-case example:
    public static void main(String[] args) {
    	translateJ translations = new translateJ();
        try {
        	translations.readTranslationFile("en", "lang_en.txt");
        	translations.readTranslationFile("test", "lang_test.txt");
            System.out.println(translations.translate("greeting", "en"));
            System.out.println(translations.translate("greeting", "test"));
            System.out.println(translations.translate("leav ing", "en"));
            System.out.println(translations.translate("leav ing", "test"));
            System.out.println(translations.translate("does not exist", "en"));
            System.out.println(translations.translate("does not exist", "test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        translateJ translated = new translateJ("KEY_SEP", "[test] FAILED TO FIND: ");
        try {
        	translated.readTranslationFile("en", "lang_en.txt");
        	translated.readTranslationFile("test", "lang_test.txt");
            System.out.println(translated.translate("greeting", "en"));
            System.out.println(translated.translate("greeting", "test"));
            System.out.println(translated.translate("leav ing", "en"));
            System.out.println(translated.translate("leav ing", "test"));
            System.out.println(translated.translate("does not exist", "en"));
            System.out.println(translated.translate("does not exist", "test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}