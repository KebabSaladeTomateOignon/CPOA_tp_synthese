package tpsynthese;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.Arch;
import simbad.sim.BallAgent;
import simbad.sim.BlockWorldObject;
import simbad.sim.Box;
import simbad.sim.CherryAgent;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

/**
 * Classe MyEnv : Environnement dans lequel les robots évoluent
 * 
 * @author : Portal
 * @version : 1.0 
 * date : 17/12/2020
 */

public class MyEnv extends EnvironmentDescription 
{

	ArrayList<MyRobot> listeRobots; // L'attribut renseigne sur la liste de robots.
	ArrayList<BlockWorldObject> alWall; // L'attribut renseigne sur la liste de murs.
	ArrayList<BlockWorldObject>  alBox; // L'attribut renseigne sur la liste de boxs.
	ArrayList<BlockWorldObject> alArch; // L'attribut renseigne sur la liste d'arches.
	ArrayList<CherryAgent> alCerise; // L'attribut renseigne sur la liste de cerises.
	ArrayList<BallAgent> alBalle; // L'attribut renseigne sur la liste de balles.
	ArrayList<Vector3d> alCeriseV3d; // L'attribut renseigne sur la liste de V3D de cerise.
	private HashMap<String, ArrayList<BlockWorldObject>> envConfig; 
	// L'attribut renseigne sur la clé d'un objet avec une liste de BlockWorldObject; 
	private String niveau; // L'attribut renseigne le niveau.
	private int secondes; // L'attribut renseigne sur le nombre de secondes.
	
	/**
	 * Constructeur MyEnv
	 * @param niveau : Le niveau de l'environnement
	 */
	public MyEnv(String niveau) 
	{
		this.niveau = niveau;
		this.secondes = 0;
		listeRobots = new ArrayList<MyRobot>();
		alWall = new ArrayList<BlockWorldObject>();
		alBox  = new ArrayList<BlockWorldObject>();
		alArch = new ArrayList<BlockWorldObject>(); 
		alCerise = new ArrayList<CherryAgent>();
		alBalle = new ArrayList<BallAgent>();
		alCeriseV3d = new ArrayList<Vector3d>();
		envConfig = new HashMap<String, ArrayList<BlockWorldObject>>();
		setUsePhysics(true);

		try {
			Scanner sc = new Scanner(new FileInputStream("./src/tpsynthese/donnees/"+this.niveau+".txt"));
			Scanner ligne;

			while (sc.hasNextLine()) {

				ligne = new Scanner(sc.nextLine());
				ligne.useDelimiter(" ");

				switch (ligne.next()) {

				case "W":
					double x = Double.parseDouble(ligne.next());
					double y = Double.parseDouble(ligne.next());
					double z = Double.parseDouble(ligne.next());
					float l = Float.parseFloat(ligne.next());
					int r = Integer.parseInt(ligne.next()) == 1 ? 1 : 0;
					float h = Float.parseFloat(ligne.next());

					BlockWorldObject w = new Wall(new Vector3d(x, y, z), l, h, this);

					w.rotate90(r);
					
					alWall.add(w);
					
					
					break;
					
				case "B":
					double x1 = Double.parseDouble(ligne.next());
					double y1 = Double.parseDouble(ligne.next());
					double z1 = Double.parseDouble(ligne.next());
					float x2 = Float.parseFloat(ligne.next());
					float y2 = Float.parseFloat(ligne.next());
					float z2 = Float.parseFloat(ligne.next());

					Box b = new Box(new Vector3d(x1, y1, z1), new Vector3f(x2, y2, z2), this);

					alBox.add(b);
					break;
				case "A":
					double x3 = Double.parseDouble(ligne.next());
					double y3 = Double.parseDouble(ligne.next());
					double z3 = Double.parseDouble(ligne.next());
					
					Arch a = new Arch(new Vector3d(x3, y3, z3), this);
					
					alArch.add(a);
					break;
					 
				case "R":
					double xR = Double.parseDouble(ligne.next());
					double yR = Double.parseDouble(ligne.next());
					double zR = Double.parseDouble(ligne.next());
					String nomR = ligne.next();

					listeRobots.add(new MyRobot(new Vector3d(xR, yR, zR), nomR,this));
					break;
					
				case "C":
					double x4 = Double.parseDouble(ligne.next());
					double y4 = Double.parseDouble(ligne.next());
					double z4 = Double.parseDouble(ligne.next());
					String nom = ligne.next();
					
					Vector3d v1 = new Vector3d(x4, y4, z4);
					
					alCerise.add(new CherryAgent(v1, nom, 0.15f,0.25f));
					alCeriseV3d.add(v1);
					break;
					
				case "BL" : 
					double x5 = Double.parseDouble(ligne.next());
					double y5 = Double.parseDouble(ligne.next());
					double z5 = Double.parseDouble(ligne.next());
					Color3f c = new Color3f(0.6f,0.5f,0.3f);
					alBalle.add(new BallAgent(new Vector3d(x5, y5,z5), "ball",c ,0.19f,0.25f));
					break;
					
				case "TM" :
					this.secondes = ligne.nextInt();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (MyRobot myRobot : listeRobots)
			add(myRobot);
		
		envConfig.put("Wall", alWall);
		envConfig.put("Box", alBox);
		
		for (BallAgent bl : alBalle) {
			add(bl);
			
		}
		
		
		for(CherryAgent mesCerises : alCerise)
			add(mesCerises);
		
		for (String objet : envConfig.keySet()) {
			for (BlockWorldObject blockWorldObject : envConfig.get(objet)) {
				add(blockWorldObject);
			}
		}
	}
	
	/**
	 * @param indice : l'indice du vecteur 3D.
	 * @return Retourne un vecteur 3D d'une position d'une cerise. 
	 */
	public Vector3d getPosition(int indice)
	{
		return this.alCeriseV3d.get(indice);

	}
	
	/**
	 * @return Retourne true si le robot a gagn� le niveau
	 */
	public boolean getGagne()
	{
		for(MyRobot rbt : this.listeRobots)
		{
			if ( rbt.getName().equals("joueur") )
			{
				return rbt.getGagne();
			}
		}
		
		return false;
	}
	
	/**
	 * @return Retourne le nombre de secondes fix�s dans le fichier niveau txt.
	 */
	public int getSecondes()
	{
		return this.secondes;
	}
	
}
