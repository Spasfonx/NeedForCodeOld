package fr.needforcode.tests.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.circuit.factory.CircuitFactory;
import fr.needforcode.course.Course;
import fr.needforcode.course.CourseRunningException;
import fr.needforcode.course.ParticipationEquipeException;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.equipe.EquipeCamille;
import fr.needforcode.equipe.EquipeDefault;
import fr.needforcode.voiture.VoitureException;
/**
 * Class test intégration d'une course avec swing (jFrame)
 * @author camille
 *
 */
public class mainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * 
	 */
	public mainFrame() throws VoitureException, InterruptedException, CourseRunningException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1024, 810);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//initialisation de la course
		String filename = "./trk/testCam.trk";
		CircuitFactory cf = new CircuitFactory(filename);
		final Circuit ci = cf.build();
		final Course maCourse = new Course(ci,2);
		final Equipe e1 = new EquipeCamille("EquipeCamille",maCourse);
		final Equipe e2 = new EquipeDefault("EquipeDefault",maCourse);
		maCourse.addEquipe(e1);
		maCourse.addEquipe(e2);
		
		final JLabel imgCircuitContainer = new JLabel("Circuit");
		final BufferedImage im = TerrainTools.imageFromCircuit(ci);
		imgCircuitContainer.setIcon(new ImageIcon(im));
		imgCircuitContainer.setBounds(0, 0, 1024, 768);
		contentPane.add(imgCircuitContainer);
		
		
		/*
		
    	for(int i = 0; i < p.champsDeVision.length;i++){
			for(int j = 0 ; j< p.champsDeVision[0].length;j++){
				System.out.print(p.champsDeVision[i][j].toString().substring(0,1));
			}
			System.out.println("");
		}
		
		*/
		
		/*	Comme le comportement n'est pas codé, il faut déterminer toutes les commandes manuelement :
		*	(Une seule arrayList aurait suffit, mais le découpage en plusieurs arrayList permet de visualiser 
		*	plus facilement quelles commandes font quoi en temps réel
		*/
		
		/*
		final ArrayList<ArrayList<Commande>> coms = new ArrayList<ArrayList<Commande>>(); //Contiendra toutes les commandes a executer
		
		//on determine les ArrayList de commandes :
		final ArrayList<Commande> c1 = new ArrayList<Commande>();
        for(int i=0; i<200; i++) c1.add(new Commande(1,0.005)); //tout droit à fond en serrant a gauche
        
        final ArrayList<Commande> c2 = new ArrayList<Commande>();
       for(int i=0; i<200; i++) c2.add(new Commande(0.1,0.065)); // tourner large à gauche sans accélerer
        
        final ArrayList<Commande> c3 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<200; i++) c3.add(new Commande(1,0));
       
        final ArrayList<Commande> c4 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<200; i++) c4.add(new Commande(0.3,-0.03));
        
        final ArrayList<Commande> c5 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<100; i++) c5.add(new Commande(1,0.001));
        
        final ArrayList<Commande> c6 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<100; i++) c6.add(new Commande(0.1,0.065));
        
        // on ajoute toutes les ArrayList de commande à l'ArrayList générale
        coms.add(c1);
        coms.add(c2);
        coms.add(c3);
        coms.add(c4);
        coms.add(c5);
        coms.add(c6);
        //etc....
         */
        
        //définition d'un nouveau Thread afin d'executer la course en parralele de la jFrame
        new Thread() {        	
        	@Override
        	public void run(){
        		/*
        		long time = java.lang.System.currentTimeMillis();
        		int i = 1; //compteur de liste
		        for(ArrayList<Commande> cg:coms){
		            System.out.println("Liste de commandes c" + i + " en cours d'exécution..."); //affichage de la liste en cours
		            try {
		            	int cpt = 0;
		            	for(Commande c:cg){
		            		
		            		Color couleur;
		            		v.piloter(c);
		            		//v2.piloter(c);
				            //System.out.println("position v1 : "+ v.getPosition());
				            //System.out.println("Direction v1 : "+ v.getDirection());
		            		//System.out.println("Vitesse v1 : "+ v.getVitesse());
		            		//si on derape, tracé rouge, sinon orange
		            		if(v.getDerapage())
								im.setRGB((int) v.getPosition().getY(), (int) v.getPosition().getX(), Color.red.getRGB());
							else
								im.setRGB((int) v.getPosition().getY(), (int) v.getPosition().getX(), Color.orange.getRGB());
		            		
//		            		if(v2.getDirection().getX() > 0)
//								im.setRGB((int) v2.getPosition().getY(), (int) v2.getPosition().getX(), Color.red.getRGB());
//							else
//								im.setRGB((int) v2.getPosition().getY(), (int) v2.getPosition().getX(), Color.orange.getRGB());
		            		switch(cpt % 8){
		            		case 0:
		            			couleur = Color.blue;
		            			break;
		            		case 1:
		            			couleur = Color.cyan;
		            			break;
		            		case 2:
		            			couleur = Color.green;
		            			break;
		            		case 3:
		            			couleur = Color.lightGray;
		            			break;
		            		case 4:
		            			couleur = Color.magenta;
		            			break;
		            		case 5:
		            			couleur = Color.orange;
		            			break;
		            		case 6:
		            			couleur = Color.pink;
		            			break;
		            		default:
		            			couleur = Color.red;
		            			break;
		            		}
		            		p.refreshChampsDeVision(couleur);
				            imgCircuitContainer.repaint();
				            Thread.sleep(10); //vitesse de l'animation en nbre de milliseconde par tracé (baisser pour accélerer le tracé)
				            cpt++;
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
		        //System.out.println("Temps écoulé : " + (java.lang.System.currentTimeMillis() - time));
		        */
        		try {
					maCourse.avancer();
					im.setRGB((int) maCourse.getVoiture(e1).getPosition().getY(), (int) maCourse.getVoiture(e1).getPosition().getX(), Color.red.getRGB());
					im.setRGB((int) maCourse.getVoiture(e2).getPosition().getY(), (int) maCourse.getVoiture(e2).getPosition().getX(), Color.yellow.getRGB());
					Terrain[][] cdv = maCourse.getChampsDeVision(e2);
					File ff=new File("cdv.txt");
					ff.createNewFile();
					FileWriter ffw=new FileWriter(ff);
			    	for(int i = 0; i < cdv.length;i++){
						for(int j = 0 ; j< cdv[0].length;j++){
								ffw.write(TerrainTools.charFromTerrain(cdv[i][j]));	
								System.out.print(TerrainTools.charFromTerrain(cdv[i][j]));
						}
						ffw.write("\n");
						System.out.println();
					}
			    	ffw.close();
				} catch (VoitureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParticipationEquipeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

        	}
        		
        }.start();

	
	    //im.setRGB(0,0, Color.orange.getRGB());
	    //ImageIO.write(im, "png", new File("test.png"));
		}
}
