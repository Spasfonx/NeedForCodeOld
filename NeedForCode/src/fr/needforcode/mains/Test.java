package fr.needforcode.mains;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.circuit.factory.CircuitFactory;
import fr.needforcode.voiture.Commande;
import fr.needforcode.voiture.Voiture;
import fr.needforcode.voiture.VoitureException;
import fr.needforcode.voiture.factory.MiageCar;
import fr.needforcode.voiture.factory.VoitureFactory;

public class Test {
	 public static void main(String[] args) throws VoitureException, IOException {
         String filename = "trk/1_safe.trk";
         
         CircuitFactory cf = new CircuitFactory(filename);
         Circuit track = cf.build();
         MiageCar mc = new MiageCar(track);
         Voiture v = mc.build();
         System.out.println("point de départ : " + track.getPointDepart());
         System.out.println("Voiture position départ : " + v.getPosition());
         
         System.out.println("Direction point de départ : "+ track.getDirectionDepart());
         System.out.println("Voiture direction départ : " + v.getDirection());
         
         BufferedImage im = TerrainTools.imageFromCircuit(track);

         ArrayList<Commande> coms = new ArrayList<Commande>();
         for(int i=0; i<200; i++) coms.add(new Commande(1,0)); // accel a fond
         for(int i=0; i<100; i++) coms.add(new Commande(0.5,0.1)); // accel a fond + gauche
         for(int i=0; i<50; i++) coms.add(new Commande(1,0)); // accel a fond
         for(int i=0; i<100; i++) coms.add(new Commande(0.5,0.1)); // accel a fond + gauche


         for(Commande c:coms){
                 v.piloter(c);
                 System.out.println("position : "+ v.getPosition());
                 if(v.getPosition().getY()<track.getWidth() && v.getPosition().getX() < track.getHeight())
                	 im.setRGB((int) v.getPosition().getY(),(int) v.getPosition().getX() , Color.orange.getRGB());
         }

         im.setRGB(0,0, Color.orange.getRGB());
         ImageIO.write(im, "png", new File("test.png"));
 }
}
