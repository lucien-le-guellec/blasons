import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class parseur {
    public static void main(String[] args) throws IOException {
        FileWriter armorialJSON = new FileWriter("src\\main\\resources\\armorial.json");
        armorialJSON.write("{\n");
        HashMap<String, String> armorialMap = new HashMap<>();
        String image, blason;
        // charger le fichier
        File html = new File("src\\main\\resources\\armorial.html");
        Document page = Jsoup.parse(html, "UTF-8", "https://fr.wikipedia.org/wiki/Armorial_des_familles_de_France");
        Elements lignes = page.select("tr");
        //System.out.println(lignes.get(1).select("td").get(0).select("img").get(0).attr("src"));
        // séparer les lignes
        for (Element ligne:lignes) {
            if (ligne.select("td").size()>0) {
                if (ligne.select("td").get(0).select("img").size()>0)
                    image = ligne.select("td").get(0).select("img").get(0).attr("src");
                else
                    image = "aucune image";
                if (ligne.select("td").get(1).select("i").size()>0) {
                    blason = ligne.select("td").get(1).select("i").get(0).text().replaceAll("\"","");
                    if (armorialMap.containsKey(image))
                        armorialMap.put(blason, blason);
                    else
                        armorialMap.put(image, blason);
                    armorialJSON.write("\t\""+image+"\": \""+blason+"\",\n");
                    armorialJSON.flush();
                }
            }
        }
        armorialJSON.write("}");
        armorialJSON.flush();
        // serialiser map
        File mapObject =  new File("src\\main\\resources\\armorial.dat") ;
        ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(mapObject)) ;
        oos.writeObject(armorialMap) ;
        // fusionner texte
        HashMap<String, Integer> compteur = new HashMap<>();
        String[] mots;
        for (Map.Entry<String,String> paire:armorialMap.entrySet()){
            mots = paire.getValue()
                    .replaceAll("’", "'")
                    .replaceAll("ʼ", "'")
                    .replaceAll("'", "' ")
                    .replaceAll(",", "")
                    .replaceAll("\\.", "")
                    .toLowerCase()
                    .split(" ");
            for (String mot:mots)
                if (compteur.containsKey(mot))
                    compteur.put(mot, compteur.get(mot)+1);
                else
                    compteur.put(mot, 1);
        }
        TreeMap<Integer, ArrayList<String>> compte = new TreeMap<>();
        ArrayList<String> listeInit;
        for (Map.Entry<String,Integer> paire:compteur.entrySet()){
            if (compte.containsKey(paire.getValue()))
                compte.get(paire.getValue()).add(paire.getKey());
            else{
                listeInit = new ArrayList<>();
                listeInit.add(paire.getKey());
                compte.put(paire.getValue(), listeInit);
            }
        }

        for (Map.Entry<Integer, ArrayList<String>> paire:compte.entrySet()){
            System.out.print(paire.getKey());
            System.out.print(": ");
            for (String mot:paire.getValue()){
                System.out.print(mot+" ");
            }
            System.out.print("\n");
        }
    }
}
