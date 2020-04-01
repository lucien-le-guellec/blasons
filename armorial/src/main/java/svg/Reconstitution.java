package svg;

import analyse.AnalyseurSyntaxique;
import analyse.TesteurExpression;
import modele.Blason;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Element;
import structures.Couleur;
import structures.Partition;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class Reconstitution {

    private static void ecrireSVG(String chemin, String nom, Document xml){
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

    public static void reconstituer (Blason blason){
        // lecture
        Partition partition = blason.GetQuartierCourant().GetChamp().GetPartition();
        Couleur couleur = blason.GetQuartierCourant().GetChamp().GetCouleurs().get(0);

        // racine
        Element racine = racine();
        Namespace xmlns = racine.getNamespace();
        Namespace inkscape = racine.getNamespace("inkscape");
        Namespace sodipodi = racine.getNamespace("sodipodi");
        Namespace xlink = racine.getNamespace("xlink");

        // fond
        Element fond = new Element("g", xmlns);
        fond.setAttribute("id", "layer4");
        fond.setAttribute("label", "Fond \u00E9cu", inkscape);
        fond.setAttribute("groupmode", "layer", inkscape);
        Element useFond = new Element("use", xmlns);
        useFond.setAttribute("href","#shield", xlink);
        useFond.setAttribute("fill", jolieCouleur(couleur)); //couleur
        fond.addContent(useFond);
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

        Document doc=new Document();
        doc.setRootElement(racine);
        ecrireSVG("SVG/", "test", doc);
    }

    public static void main(String[] args){
        AnalyseurSyntaxique a = new AnalyseurSyntaxique("D'argent plein.");
        a.Analyser();
        Blason b = a.GetBlason();
        reconstituer(b);
    }
}
