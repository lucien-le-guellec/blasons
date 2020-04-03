package svg;

import analyse.AnalyseurSyntaxique;
import analyse.TesteurExpression;
import modele.Blason;

import modele.Quartier;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Element;
import ressources.Partitions;
import structures.Couleur;
import structures.Partition;

import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class Reconstitution {

    private Element racine;
    private Namespace xmlns;
    private Namespace inkscape;
    private Namespace sodipodi;
    private Namespace xlink;
    private Blason blason;

    public Reconstitution(Blason blason){
        this.blason = blason;

        // lecture
        Partition partitionnement = blason.GetPartitionnement();

        // racine
        racine = racine();
        xmlns = racine.getNamespace();
        inkscape = racine.getNamespace("inkscape");
        sodipodi = racine.getNamespace("sodipodi");
        xlink = racine.getNamespace("xlink");

        // fond
        Element fond = new Element("g", xmlns);
        fond.setAttribute("id", "layer4");
        fond.setAttribute("label", "Fond", inkscape);
        fond.setAttribute("groupmode", "layer", inkscape);
        fond.setAttribute("clip-path", "url(#shield_cut)");
        fond.setAttribute("style", "stroke:#000;stroke-width:3");
        if (blason.GetQuartiers().size() == 1) {
            Partition partition = blason.GetQuartierCourant().GetChamp().GetPartition();
            Couleur[] couleurs = blason.GetQuartierCourant().GetChamp().GetCouleurs().toArray(new Couleur[0]);
            if (partition == null)
                fond.addContent(fondPlein(couleurs));
            else
                switch (partition.GetPartition()) {
                    case PARTI:
                        for (Element partie : fondParti(couleurs))
                            fond.addContent(partie);
                        break;
                    case COUPE:
                        for (Element partie : fondCoupe(couleurs))
                            fond.addContent(partie);
                        break;
                    case TAILLE:
                        for (Element partie : fondTaille(couleurs))
                            fond.addContent(partie);
                        break;
                    case TRANCHE:
                        for (Element partie : fondTranche(couleurs))
                            fond.addContent(partie);
                        break;
                    case ECARTELE:
                        for (Element partie : fondEcartele(couleurs))
                            fond.addContent(partie);
                        break;

                }

        } else {
            Quartier[] quartiers = blason.GetQuartiers().toArray(new Quartier[0]);
            switch (partitionnement.GetPartition()) {
                case PARTI:
                    for (Element partie : fondParti(quartiers))
                        fond.addContent(partie);
                    break;
                case COUPE:
                    for (Element partie : fondCoupe(quartiers))
                        fond.addContent(partie);
                    break;
                case TAILLE:
                    for (Element partie : fondTaille(quartiers))
                        fond.addContent(partie);
                    break;
                case TRANCHE:
                    for (Element partie : fondTranche(quartiers))
                        fond.addContent(partie);
                    break;
                case ECARTELE:
                    for (Element partie : fondEcartele(quartiers))
                        fond.addContent(partie);
                    break;
                case ECARTELE_SAUTOIR:
                    for (Element partie : fondEcarteleSautoir(quartiers))
                        fond.addContent(partie);
                    break;

                }

        }
        racine.addContent(fond);

        //meubles

        // reflet
        Element reflet = new Element("g", xmlns);
        reflet.setAttribute("id", "layer2");
        reflet.setAttribute("label", "Reflet final", inkscape);
        reflet.setAttribute("groupmode", "layer", inkscape);
        reflet.setAttribute("insensitive", "true", sodipodi);
        Element useReflet = new Element("use", xmlns);
        useReflet.setAttribute("href","#shield", xlink);
        useReflet.setAttribute("fill", "url(#Gradient1)"); //couleur
        reflet.addContent(useReflet);
        racine.addContent(reflet);

        // contour
        Element contour = new Element("g", xmlns);
        contour.setAttribute("id", "layer1");
        contour.setAttribute("label", "Contour final", inkscape);
        contour.setAttribute("groupmode", "layer", inkscape);
        contour.setAttribute("insensitive", "true", sodipodi);
        Element useContour = new Element("use", xmlns);
        useContour.setAttribute("href","#shield", xlink);
        useContour.setAttribute("style", "fill:none;stroke:#000;stroke-width:3"); //couleur
        contour.addContent(useContour);
        racine.addContent(contour);

    }

    private void ecrireSVG(String chemin, String nom){
        Document xml=new Document();
        xml.setRootElement(racine);
        XMLOutputter sortie=new XMLOutputter();
        sortie.setFormat(Format.getPrettyFormat());
        try {
            sortie.output(xml, new FileWriter(new File(chemin+nom+".svg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String jolieCouleur(Couleur c){
        String hexa = "";
        switch(c.toString()) {
            case "(0,0,255)":
                hexa = "#2B5DF2";
                break;
            case "(255,0,0)":
                hexa = "#E20909";
                break;
            case "(0,0,0)":
                hexa = "#000000";
                break;
            case "(0,255,0)":
                hexa = "#5AB532";
                break;
            case "(137,57,94)":
                hexa = "#D576AD";
                break;
            case "(255,241,0)":
                hexa = "#FCEF3C";
                break;
            case "(231,231,231)":
                hexa = "#FFFFFF";
                break;
            case "(255,128,0)":
                hexa = "#F37905";
                break;
            case "(227,197,162)":
                hexa = "#FEC3AC";
                break;
            default:
                hexa = "#FFFFFF";
                break;
        }
        return hexa;
    }

    private static Element racine(){
        // racine
        Element racine =new Element("svg");
        String racineString = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:sodipodi=\"http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd\" xmlns:inkscape=\"http://www.inkscape.org/namespaces/inkscape\" width=\"600\" height=\"660\" viewBox=\"-300 -300 600 660\">\n" +
                "\t<defs>\n" +
                "\t\t<radialGradient id=\"Gradient1\" gradientUnits=\"userSpaceOnUse\" cx=\"-80\" cy=\"-80\" r=\"405\">\n" +
                "\t\t\t<stop style=\"stop-color:#fff;stop-opacity:0.31\" offset=\"0\"/>\n" +
                "\t\t\t<stop style=\"stop-color:#fff;stop-opacity:0.25\" offset=\"0.19\"/>\n" +
                "\t\t\t<stop style=\"stop-color:#6b6b6b;stop-opacity:0.125\" offset=\"0.6\"/>\n" +
                "\t\t\t<stop style=\"stop-color:#000;stop-opacity:0.125\" offset=\"1\"/>\n" +
                "\t\t</radialGradient>\n" +
                "\t\t<clipPath id=\"shield_cut\">\n" +
                "\t\t\t<path id=\"shield\" d=\"M-298.5,-298.5 h597 v258.5 C 298.5,246.2 0,358.39 0,358.39 0,358.39 -298.5,246.2 -298.5,-40z\"/>\n" +
                "\t\t</clipPath> \n" +
                "\t</defs>\n" +
                "</svg>";
        try {
            racine = ((new SAXBuilder()).build((new StringReader(racineString))).getRootElement().detach());
        } catch (JDOMException | IOException ex) {
            ex.printStackTrace();
        }

        return racine;
    }

    private Element fondPlein(Couleur[] couleurs) {
        Element useFond = new Element("use", xmlns);
        useFond.setAttribute("href","#shield", xlink);
        useFond.setAttribute("fill", jolieCouleur(couleurs[0]));
        return useFond;
    }

    private Element[] fondCoupe(Couleur[] couleurs) {
        Element haut = new Element("rect", xmlns);
        haut.setAttribute("x","-300");
        haut.setAttribute("y","-300");
        haut.setAttribute("width","600");
        haut.setAttribute("height","300");
        haut.setAttribute("fill", jolieCouleur(couleurs[0]));
        Element bas = new Element("rect", xmlns);
        bas.setAttribute("x","-300");
        bas.setAttribute("width","600");
        bas.setAttribute("height","360");
        bas.setAttribute("fill", jolieCouleur(couleurs[1]));
        return new Element[]{haut, bas};
    }

    private Element[] fondParti(Couleur[] couleurs) {
        Element gauche = new Element("rect", xmlns);
        gauche.setAttribute("x","-300");
        gauche.setAttribute("y","-300");
        gauche.setAttribute("width","300");
        gauche.setAttribute("height","660");
        gauche.setAttribute("fill", jolieCouleur(couleurs[0]));
        Element droite = new Element("rect", xmlns);
        droite.setAttribute("y","-300");
        droite.setAttribute("width","300");
        droite.setAttribute("height","660");
        droite.setAttribute("fill", jolieCouleur(couleurs[1]));
        return new Element[]{gauche, droite};
    }

    private Element[] fondTaille(Couleur[] couleurs) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, -300 360");
        un.setAttribute("fill", jolieCouleur(couleurs[0]));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","300 -300, 300 360, -300 360");
        deux.setAttribute("fill", jolieCouleur(couleurs[1]));
        return new Element[]{un, deux};
    }

    private Element[] fondTranche(Couleur[] couleurs) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, 300 360");
        un.setAttribute("fill", jolieCouleur(couleurs[0]));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","-300 -300, 300 360, -300 360");
        deux.setAttribute("fill", jolieCouleur(couleurs[1]));
        return new Element[]{un, deux};
    }

    private Element[] fondEcartele(Couleur[] couleurs) {
        Element un = new Element("rect", xmlns);
        un.setAttribute("x","-300");
        un.setAttribute("y","-300");
        un.setAttribute("width","300");
        un.setAttribute("height","300");
        un.setAttribute("fill", jolieCouleur(couleurs[0]));
        Element deux = new Element("rect", xmlns);
        deux.setAttribute("y","-300");
        deux.setAttribute("width","300");
        deux.setAttribute("height","300");
        deux.setAttribute("fill", jolieCouleur(couleurs[1]));
        Element trois = new Element("rect", xmlns);
        trois.setAttribute("x","-300");
        trois.setAttribute("width","300");
        trois.setAttribute("height","360");
        trois.setAttribute("fill", jolieCouleur(couleurs[1]));
        Element quatre = new Element("rect", xmlns);
        quatre.setAttribute("width","300");
        quatre.setAttribute("height","360");
        quatre.setAttribute("fill", jolieCouleur(couleurs[0]));
        return new Element[]{un, deux, trois, quatre};
    }

    private Element[] fondCoupe(Quartier[] quartiers) {
        Element haut = new Element("rect", xmlns);
        haut.setAttribute("x","-300");
        haut.setAttribute("y","-300");
        haut.setAttribute("width","600");
        haut.setAttribute("height","300");
        haut.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element bas = new Element("rect", xmlns);
        bas.setAttribute("x","-300");
        bas.setAttribute("width","600");
        bas.setAttribute("height","360");
        bas.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        return new Element[]{haut, bas};
    }

    private Element[] fondParti(Quartier[] quartiers) {
        Element gauche = new Element("rect", xmlns);
        gauche.setAttribute("x","-300");
        gauche.setAttribute("y","-300");
        gauche.setAttribute("width","300");
        gauche.setAttribute("height","660");
        gauche.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element droite = new Element("rect", xmlns);
        droite.setAttribute("y","-300");
        droite.setAttribute("width","300");
        droite.setAttribute("height","660");
        droite.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        return new Element[]{gauche, droite};
    }

    private Element[] fondTaille(Quartier[] quartiers) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, -300 360");
        un.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","300 -300, 300 360, -300 360");
        deux.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        return new Element[]{un, deux};
    }

    private Element[] fondTranche(Quartier[] quartiers) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, 300 360");
        un.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","-300 -300, 300 360, -300 360");
        deux.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        return new Element[]{un, deux};
    }

    private Element[] fondEcartele(Quartier[] quartiers) {
        Element un = new Element("rect", xmlns);
        un.setAttribute("x","-300");
        un.setAttribute("y","-300");
        un.setAttribute("width","300");
        un.setAttribute("height","300");
        un.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element deux = new Element("rect", xmlns);
        deux.setAttribute("y","-300");
        deux.setAttribute("width","300");
        deux.setAttribute("height","300");
        deux.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        Element trois = new Element("rect", xmlns);
        trois.setAttribute("x","-300");
        trois.setAttribute("width","300");
        trois.setAttribute("height","360");
        trois.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        Element quatre = new Element("rect", xmlns);
        quatre.setAttribute("width","300");
        quatre.setAttribute("height","360");
        quatre.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        return new Element[]{un, deux, trois, quatre};
    }

    private Element[] fondEcarteleSautoir(Quartier[] quartiers) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, 0 0");
        un.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","-300 -300, 0 0, -300 360");
        deux.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        Element trois = new Element("polygon", xmlns);
        trois.setAttribute("points","300 -300, 0 0, 300 360");
        trois.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        Element quatre = new Element("polygon", xmlns);
        quatre.setAttribute("points","300 360, 0 0, -300 360");
        quatre.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        return new Element[]{un, deux, trois, quatre};
    }

    public static void main(String[] args){
        AnalyseurSyntaxique a = new AnalyseurSyntaxique("Coupé d'or et de gueules.");
        a.Analyser();
        Blason b = a.GetBlason();
        Reconstitution r = new Reconstitution(b);
        r.ecrireSVG("SVG/", "test");
    }
}
