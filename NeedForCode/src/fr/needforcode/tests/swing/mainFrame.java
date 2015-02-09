package fr.needforcode.tests.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.circuit.factory.CircuitFactory;
import fr.needforcode.voiture.Commande;
import fr.needforcode.voiture.Voiture;
import fr.needforcode.voiture.VoitureException;
import fr.needforcode.voiture.factory.MiageCarFactory;
/**
 * Class test int�gration d'une course avec swing (jFrame)
 * @author camille
 *
 */
public class mainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() throws VoitureException, InterruptedException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1024, 810);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//initialisation de la course
		String filename = "./trk/1_safe.trk";
		CircuitFactory cf = new CircuitFactory(filename);
		Circuit ci = cf.build();
		MiageCarFactory mc = new MiageCarFactory(ci);
		final Voiture v = mc.build();
		final JLabel imgCircuitContainer = new JLabel("Circuit");
		final BufferedImage im = TerrainTools.imageFromCircuit(ci);
		imgCircuitContainer.setIcon(new ImageIcon(im));
		imgCircuitContainer.setBounds(0, 0, 1024, 768);
		contentPane.add(imgCircuitContainer);

		/*	Comme le comportement n'est pas cod�, il faut d�terminer toutes les commandes manuelement :
		*	(Une seule arrayList aurait suffit, mais le d�coupage en plusieurs arrayList permet de visualiser 
		*	plus facilement quelles commandes font quoi en temps r�el
		*/
		
		final ArrayList<ArrayList<Commande>> coms = new ArrayList<ArrayList<Commande>>(); //Contiendra toutes les commandes a executer
		
		//on determine les ArrayList de commandes :
		final ArrayList<Commande> c1 = new ArrayList<Commande>();
        for(int i=0; i<200; i++) c1.add(new Commande(1,0.005)); //tout droit � fond en serrant a gauche
        
        final ArrayList<Commande> c2 = new ArrayList<Commande>();
        for(int i=0; i<200; i++) c2.add(new Commande(0.1,0.065)); // tourner large � gauche sans acc�lerer
        
        final ArrayList<Commande> c3 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<200; i++) c3.add(new Commande(1,0));
        
        final ArrayList<Commande> c4 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<200; i++) c4.add(new Commande(0.3,-0.03));
        
        final ArrayList<Commande> c5 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<100; i++) c5.add(new Commande(1,0.001));
        
        final ArrayList<Commande> c6 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<100; i++) c6.add(new Commande(0.1,0.065));
        
        // on ajoute toutes les ArrayList de commande � l'ArrayList g�n�rale
        coms.add(c1);
        coms.add(c2);
        coms.add(c3);
        coms.add(c4);
        coms.add(c5);
        coms.add(c6);
        //etc....
        
        //d�finition d'un nouveau Thread afin d'executer la course en parralele de la jFrame
        new Thread() {        	
        	@Override
        	public void run(){
        		int i = 1; //compteur de liste
		        for(ArrayList<Commande> cg:coms){
		            System.out.println("Liste de commandes c" + i + " en cours d'ex�cution..."); //affichage de la liste en cours
		            try {
		            	for(Commande c:cg){
		            		v.piloter(c);
				            //System.out.println("position : "+ v.getPosition());
		            		//si on derape, trac� rouge, sinon orange
		            		if(v.getDerapage())
								im.setRGB((int) v.getPosition().getY(), (int) v.getPosition().getX(), Color.red.getRGB());
							else
								im.setRGB((int) v.getPosition().getY(), (int) v.getPosition().getX(), Color.orange.getRGB());
				            imgCircuitContainer.repaint();
				            Thread.sleep(10); //vitesse de l'animation en nbre de milliseconde par trac� (baisser pour acc�lerer le trac�)
				            
		            	}
					} catch (VoitureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            i++; // liste suivante
			    }
        	}
        }.start();
	
	    //im.setRGB(0,0, Color.orange.getRGB());
	    //ImageIO.write(im, "png", new File("test.png"));
		}
}
