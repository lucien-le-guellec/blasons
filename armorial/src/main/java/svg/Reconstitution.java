package svg;

import analyse.AnalyseurSyntaxique;
import modele.Blason;

import modele.Charge;
import modele.Quartier;
import org.jdom2.*;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import structures.Couleur;
import structures.Partition;

import java.io.*;
import java.util.*;

/**
 * 	Permet la reconstitution du dessin d'un blason
 * 	et l'�crit dans un fichier au format SVG
 *
 */
public class Reconstitution {

    private Element racine;
    private Namespace xmlns;
    private Namespace inkscape;
    private Namespace sodipodi;
    private Namespace xlink;
    private Blason blason;

    /**
     * Constructeur de la classe, qui contient aussi l'op�ration de reconstitution
     * @param blason, le blason � dessiner
     */
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
        List<Element> charges = new ArrayList<>();
        if (blason.GetQuartiers().size() == 1) {
            Partition partition = blason.GetQuartierCourant().GetChamp().GetPartition();
            Couleur[] couleurs = blason.GetQuartierCourant().GetChamp().GetCouleurs().toArray(new Couleur[0]);
            if (partition == null) {
                fond.addContent(fondPlein(couleurs));
                int nCouche = 0;
                // charge
                for (Charge c : blason.GetQuartierCourant().GetChamp().GetCharges()){
                    nCouche++;
                    Element charge = new Element("g", xmlns);
                    charge.setAttribute("id", "layer3-"+nCouche);
                    charge.setAttribute("label", "Charge", inkscape);
                    charge.setAttribute("groupmode", "layer", inkscape);
                    //charge.setAttribute("style", "stroke:#000;stroke-width:3");
                    switch (c.GetRepresentation()) {
                        // pi�ces
                        case "chef":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerChef(c.GetCouleurs().get(0)));
                            break;
                        case "fasce":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerFasce(c.GetCouleurs().get(0)));
                            break;
                        case "pal":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerPal(c.GetCouleurs().get(0)));
                            break;
                        case "pals":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            for (Element pal : dessinerPals(c.GetNombre(), c.GetCouleurs().get(0)))
                                charge.addContent(pal);
                            break;
                        case "canton":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerCanton(c.GetCouleurs().get(0)));
                            break;
                        case "croix":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerCroix(c.GetCouleurs().get(0)));
                            break;
                        case "sautoir":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerSautoir(c.GetCouleurs().get(0)));
                            break;
                        case "bande":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerBande(c.GetCouleurs().get(0)));
                            break;
                        case "barre":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerBarre(c.GetCouleurs().get(0)));
                            break;
                        case "chevron":
                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
                            charge.setAttribute("clip-path", "url(#shield_cut)");
                            charge.addContent(dessinerChevron(c.GetCouleurs().get(0)));
                            break;

                        // meubles
                        case "lion":
                            charge.setAttribute("transform", "translate(-223,-298) scale(0.812,0.845)");
                            for (Element path : dessinerLion(c.GetCouleurs().get(0))){
                                charge.addContent(path.setNamespace(xmlns));
                            }
                            break;
                        case "aigle":
                            charge.setAttribute("transform", "translate(-150,-200) scale(0.42,0.42)");
                            for (Element path : dessinerAigle(c.GetCouleurs().get(0))){
                                charge.addContent(path.setNamespace(xmlns));
                            }
                            break;


//                        meubles dont le nombre peut varier
//
//                        case "coquilles":
//                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
//                            charge.addContent(dessinerChevron(c.GetCouleurs().get(0)));
//                            break;
//                        case "�cussons":
//                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
//                            charge.addContent(dessinerChevron(c.GetCouleurs().get(0)));
//                            break;
//                        case "roses":
//                            charge.setAttribute("style", "stroke:#000;stroke-width:3");
//                            charge.addContent(dessinerChevron(c.GetCouleurs().get(0)));
//                            break;
                    }
                    charges.add(charge);
                }
            }
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
        for (Element charge : charges)
            racine.addContent(charge);

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

    /**
     * �criture du fichier SVG
     * @param chemin, le dossier o� �crire le fichier
     * @param nom, le nom du fichier
     */
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

    /**
     * Modifie l�g�rement les couleurs pour qu'elles soient plus jolies sur le dessin
     * @param c, la couleur initiale
     * @return le code hexad�cimal de la jolie couleur
     */
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

    /**
     * Cr�e l'�l�ment racine de l'arbre XML
     * @return l'�l�ment initialis� avec la forme du blason et l'en-t�te <svg>, mais vide
     */
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

    /**
     * Dessine le fond d'un blason simple
     * @param couleurs, le tableau contenant la couleur du fond
     * @return l'�l�ment <use> de l'arbre XML qui dessine le fond
     */
    private Element fondPlein(Couleur[] couleurs) {
        Element useFond = new Element("use", xmlns);
        useFond.setAttribute("href","#shield", xlink);
        useFond.setAttribute("fill", jolieCouleur(couleurs[0]));
        return useFond;
    }

    /**
     * Dessine le fond d'un blason coup�
     * @param couleurs, le tableau contenant les couleur du fond
     * @return les deux �l�ments <rect> de l'arbre XML qui dessinent le fond
     */
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

    /**
     * Dessine le fond d'un blason parti
     * @param couleurs, le tableau contenant les couleur du fond
     * @return les deux �l�ments <rect> de l'arbre XML qui dessinent le fond
     */
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

    /**
     * Dessine le fond d'un blason taill�
     * @param couleurs, le tableau contenant les couleur du fond
     * @return les deux �l�ments <polygon> de l'arbre XML qui dessinent le fond
     */
    private Element[] fondTaille(Couleur[] couleurs) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, -300 360");
        un.setAttribute("fill", jolieCouleur(couleurs[0]));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","300 -300, 300 360, -300 360");
        deux.setAttribute("fill", jolieCouleur(couleurs[1]));
        return new Element[]{un, deux};
    }

    /**
     * Dessine le fond d'un blason tranch�
     * @param couleurs, le tableau contenant les couleur du fond
     * @return les deux �l�ments <polygon> de l'arbre XML qui dessinent le fond
     */
    private Element[] fondTranche(Couleur[] couleurs) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, 300 360");
        un.setAttribute("fill", jolieCouleur(couleurs[0]));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","-300 -300, 300 360, -300 360");
        deux.setAttribute("fill", jolieCouleur(couleurs[1]));
        return new Element[]{un, deux};
    }

    /**
     * Dessine le fond d'un blason �cartel�
     * @param couleurs, le tableau contenant les couleur du fond
     * @return les quatre �l�ments <rect> de l'arbre XML qui dessinent le fond
     */
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

    /**
     * Dessine le fond d'un blason coup� en plusieurs quartiers
     * @param quartiers, le tableau contenant les quartiers du blason
     * @return les deux �l�ments <rect> de l'arbre XML qui dessinent le fond
     */
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

    /**
     * Dessine le fond d'un blason parti en plusieurs quartiers
     * @param quartiers, le tableau contenant les quartiers du blason
     * @return les deux �l�ments <rect> de l'arbre XML qui dessinent le fond
     */
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

    /**
     * Dessine le fond d'un blason taill� en plusieurs quartiers
     * @param quartiers, le tableau contenant les quartiers du blason
     * @return les deux �l�ments <polygon> de l'arbre XML qui dessinent le fond
     */
    private Element[] fondTaille(Quartier[] quartiers) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, -300 360");
        un.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","300 -300, 300 360, -300 360");
        deux.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        return new Element[]{un, deux};
    }

    /**
     * Dessine le fond d'un blason tranch� en plusieurs quartiers
     * @param quartiers, le tableau contenant les quartiers du blason
     * @return les deux �l�ments <polygon> de l'arbre XML qui dessinent le fond
     */
    private Element[] fondTranche(Quartier[] quartiers) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, 300 360");
        un.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","-300 -300, 300 360, -300 360");
        deux.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        return new Element[]{un, deux};
    }

    /**
     * Dessine le fond d'un blason �cartel� en plusieurs quartiers
     * @param quartiers, le tableau contenant les quartiers du blason
     * @return les quatre �l�ments <rect> de l'arbre XML qui dessinent le fond
     */
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
        trois.setAttribute("fill", jolieCouleur(quartiers[2].GetChamp().GetCouleurs().get(0)));
        Element quatre = new Element("rect", xmlns);
        quatre.setAttribute("width","300");
        quatre.setAttribute("height","360");
        quatre.setAttribute("fill", jolieCouleur(quartiers[3].GetChamp().GetCouleurs().get(0)));
        return new Element[]{un, deux, trois, quatre};
    }

    /**
     * Dessine le fond d'un blason �cartel� en sautoir en plusieurs quartiers
     * @param quartiers, le tableau contenant les quartiers du blason
     * @return les quatre �l�ments <polygon> de l'arbre XML qui dessinent le fond
     */
    private Element[] fondEcarteleSautoir(Quartier[] quartiers) {
        Element un = new Element("polygon", xmlns);
        un.setAttribute("points","300 -300, -300 -300, 0 0");
        un.setAttribute("fill", jolieCouleur(quartiers[0].GetChamp().GetCouleurs().get(0)));
        Element deux = new Element("polygon", xmlns);
        deux.setAttribute("points","-300 -300, 0 0, -300 360");
        deux.setAttribute("fill", jolieCouleur(quartiers[1].GetChamp().GetCouleurs().get(0)));
        Element trois = new Element("polygon", xmlns);
        trois.setAttribute("points","300 -300, 0 0, 300 360");
        trois.setAttribute("fill", jolieCouleur(quartiers[2].GetChamp().GetCouleurs().get(0)));
        Element quatre = new Element("polygon", xmlns);
        quatre.setAttribute("points","300 360, 0 0, -300 360");
        quatre.setAttribute("fill", jolieCouleur(quartiers[3].GetChamp().GetCouleurs().get(0)));
        return new Element[]{un, deux, trois, quatre};
    }

    /**
     * Dessine le chef d'un blason simple
     * @param couleur, la couleur du chef
     * @return l'�l�ment <rect> de l'arbre XML qui dessine le chef
     */
    private Element dessinerChef(Couleur couleur) {
        Element rect = new Element("rect", xmlns);
        rect.setAttribute("x","-300");
        rect.setAttribute("y","-300");
        rect.setAttribute("width","600");
        rect.setAttribute("height","180");
        rect.setAttribute("fill", jolieCouleur(couleur));
        return rect;
    }

    /**
     * Dessine la fasce d'un blason simple
     * @param couleur, la couleur de la fasce
     * @return l'�l�ment <rect> de l'arbre XML qui dessine la fasce
     */
    private Element dessinerFasce(Couleur couleur) {
        Element rect = new Element("rect", xmlns);
        rect.setAttribute("x","-300");
        rect.setAttribute("y","-87");
        rect.setAttribute("width","600");
        rect.setAttribute("height","134");
        rect.setAttribute("fill", jolieCouleur(couleur));
        return rect;
    }

    /**
     * Dessine un pal sur un blason simple
     * @param couleur, la couleur du pal
     * @return l'�l�ment <rect> de l'arbre XML qui dessine le pal
     */
    private Element dessinerPal(Couleur couleur) {
        Element rect = new Element("rect", xmlns);
        rect.setAttribute("x","-67");
        rect.setAttribute("y","-300");
        rect.setAttribute("width","134");
        rect.setAttribute("height","660");
        rect.setAttribute("fill", jolieCouleur(couleur));
        return rect;
    }

    /**
     * Dessine plusieurs pals sur un blason simple
     * @param nombre, le nombre de pals
     * @param couleur, la couleur des pals
     * @return les �l�ments <rect> de l'arbre XML qui dessinent les pals
     */
    private Element[] dessinerPals(int nombre, Couleur couleur) {
        Element[] pals = new Element[nombre];
        float largeur = (float) (600.0/(2*nombre+1));
        for (int i=0; i<nombre; i++){
            Element rect = new Element("rect", xmlns);
            rect.setAttribute("x",String.valueOf(2*largeur*i+largeur-300));
            rect.setAttribute("y","-300");
            rect.setAttribute("width",String.valueOf(largeur));
            rect.setAttribute("height","660");
            rect.setAttribute("fill", jolieCouleur(couleur));
            pals[i] = rect;
        }
        return pals;
    }

    /**
     * Dessine le canton d'un blason simple
     * @param couleur, la couleur du canton
     * @return l'�l�ment <rect> de l'arbre XML qui dessine le canton
     */
    private Element dessinerCanton(Couleur couleur) {
        Element rect = new Element("rect", xmlns);
        rect.setAttribute("x","-300");
        rect.setAttribute("y","-300");
        rect.setAttribute("width","180");
        rect.setAttribute("height","180");
        rect.setAttribute("fill", jolieCouleur(couleur));
        return rect;
    }

    /**
     * Dessine une croix sur un blason simple
     * @param couleur, la couleur de la croix
     * @return l'�l�ment <polygon> de l'arbre XML qui dessine la croix
     */
    private Element dessinerCroix(Couleur couleur) {
        Element croix = new Element("polygon", xmlns);
        croix.setAttribute("points","-67 -300, 67 -300, 67 -87, 300 -87, 300 47, 67 47, 67 660, -67 660, -67 47, -300 47, -300 -87, -67 -87");
        croix.setAttribute("fill", jolieCouleur(couleur));
        return croix;
    }

    /**
     * Dessine un sautoir sur un blason simple
     * @param couleur, la couleur du sautoir
     * @return l'�l�ment <polygon> de l'arbre XML qui dessine le sautoir
     */
    private Element dessinerSautoir(Couleur couleur) {
        Element sautoir = new Element("polygon", xmlns);
        sautoir.setAttribute("points","-300 -300, -205 -300, 0 -95, 205 -300, 300 -300, 300 -195, 95 0, 300 205, 205 300, 0 95, -205 300, -300 205, -95 0, -300 -205");
        sautoir.setAttribute("fill", jolieCouleur(couleur));
        return sautoir;
    }

    /**
     * Dessine une bande sur un blason simple
     * @param couleur, la couleur de la bande
     * @return l'�l�ment <polygon> de l'arbre XML qui dessine la bande
     */
    private Element dessinerBande(Couleur couleur) {
        Element bande = new Element("polygon", xmlns);
        bande.setAttribute("points","-300 -300, -205 -300, 300 205, 205 300, -300 -205");
        bande.setAttribute("fill", jolieCouleur(couleur));
        return bande;
    }

    /**
     * Dessine une barre sur un blason simple
     * @param couleur, la couleur de la barre
     * @return l'�l�ment <polygon> de l'arbre XML qui dessine la barre
     */
    private Element dessinerBarre(Couleur couleur) {
        Element barre = new Element("polygon", xmlns);
        barre.setAttribute("points","205 -300, 300 -300, 300 -195, -205 300, -300 205");
        barre.setAttribute("fill", jolieCouleur(couleur));
        return barre;
    }

    /**
     * Dessine un chevron sur un blason simple
     * @param couleur, la couleur du chevron
     * @return l'�l�ment <polygon> de l'arbre XML qui dessine le chevron
     */
    private Element dessinerChevron(Couleur couleur) {
        Element chevron = new Element("polygon", xmlns);
        chevron.setAttribute("points","0 -198, 300 123, 300 272, 0 -26, -300 272, -300 123");
        chevron.setAttribute("fill", jolieCouleur(couleur));
        return chevron;
    }

    /**
     * Dessine un lion sur un blason simple
     * @param couleur, la couleur du lion
     * @return les �l�ments <path> de l'arbre XML qui dessinent le lion
     */
    private List<Element> dessinerLion(Couleur couleur)  {
        Element lion = new Element("g", xmlns);
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = new FileInputStream("SVG/lion.svg");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String lionString = sb.toString().replace("?couleur", jolieCouleur(couleur));
        try {
            lion = ((new SAXBuilder()).build((new StringReader(lionString))).getRootElement().detach());
        } catch (JDOMException | IOException ex) {
            ex.printStackTrace();
        }

        return lion.removeContent(Filters.element());
    }

    /**
     * Dessine une aigle sur un blason simple
     * @param couleur, la couleur de l'aigle
     * @return les �l�ments <path> de l'arbre XML qui dessinent l'aigle
     */
    private List<Element> dessinerAigle(Couleur couleur)  {
        Element aigle = new Element("g", xmlns);
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = new FileInputStream("SVG/aigle.svg");
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String aigleString = sb.toString().replace("?couleur1", jolieCouleur(couleur)).replace("?couleur2", jolieCouleur(couleur));
        try {
            aigle = ((new SAXBuilder()).build((new StringReader(aigleString))).getRootElement().detach());
        } catch (JDOMException | IOException ex) {
            ex.printStackTrace();
        }

        return aigle.removeContent(Filters.element());
    }


    /**
     * Fonction principale servant � essayer la classe
     */
    public static void main(String[] args){
        AnalyseurSyntaxique a = new AnalyseurSyntaxique("d'or � quatre pals de gueules");
        a.Analyser();
        Blason b = a.GetBlason();
        Reconstitution r = new Reconstitution(b);
        r.ecrireSVG("SVG/", "test");
    }
}
