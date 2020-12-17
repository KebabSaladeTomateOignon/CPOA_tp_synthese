package tpsynthese;


import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.BallAgent;
import simbad.sim.CherryAgent;

import simbad.sim.SimpleAgent;

/**
 * Classe MyRobot : Gérer le robot
 * 
 * @author : Portal
 * @version : 1.0 
 * date : 17/12/2020
 */

public class MyRobot extends Agent {

	private double speed; // L'attribut renseigne sur la vitesse du robot.
	private MyEnv env; // L'attribut renseigne sur l'environnement du robot.
	private String nomR; // L'attribut renseigne sur le nom du robot.
	private boolean aGagne; // L'attribut renseigne si le robot a gagné.
	
	/**
	 * Constructeur MyRobot
	 * 
	 * @param position : la position du robot.
	 * @param name     : le nom du robot.
	 * @param env      : l'environnement du robot.
	 */
	public MyRobot(Vector3d position, String name, MyEnv env) {
		super(position, name);
		this.speed = 0;
		this.env = env;
		this.nomR = name;
		this.aGagne = false;
	}

	/**
	 * Initialisation du robot
	 */
	public void initBehavior() {
	}

	/**
	 * Mouvement du robot
	 */
	public void performBehavior() {
		// Si un agent est proche
		if (anOtherAgentIsVeryNear()) {
			SimpleAgent agent = getVeryNearAgent();

			// Si c'est une Cherry
			if (agent instanceof CherryAgent) {
				System.out.println(agent.getName());

				Vector3d v = new Vector3d();
				
				if (agent.getName().equals("tp1"))
				{
					v = env.getPosition(1);
					v.setZ(v.getZ() - 1);
					moveToPosition(v);
					rotateY(2);
					v.setZ(v.getZ() + 1);
				}
				if (agent.getName().equals("tp2"))
				{
					v = env.getPosition(0);
					v.setX(v.getX() - 1);
					moveToPosition(v);
					rotateY(4);
					v.setX(v.getX() + 1);
				}
				if (agent.getName().equals("tp3"))
				{
					v = env.getPosition(3);
					v.setZ(v.getZ() - 1);
					moveToPosition(v);
					rotateY(-2);
					v.setZ(v.getZ() + 1);
				}
				if (agent.getName().equals("tp4"))
				{
					v = env.getPosition(2);
					v.setX(v.getX() - 1);
					moveToPosition(v);
					rotateY(-4);
					v.setX(v.getX() + 1);
				}
			}
			
			if ( agent.getName().equals("fin") ) 
			{
				this.aGagne = true;
				agent.detach();
			}

		}
		if (anOtherAgentIsVeryNear())
		{
			SimpleAgent agent = getVeryNearAgent();
			if (agent instanceof BallAgent && nomR.equals("objectif"))
			{
				this.env.alWall.get(this.env.alWall.size()-1).detach();
			}
		}

		setTranslationalVelocity(speed);
	}

	/**
	 * @return Retourne la vitesse du robot.
	 */
	public double getSpeed() {
		return this.speed;
	}
	
	/**
	 * @return Retourne true si le robot a remplit l'objectif.
	 */
	public boolean getGagne()
	{
		return this.aGagne;
	}

	/**
	 * Fixe la vitesse du robot.
	 * 
	 * @param speed : la vitesse du robot à mettre.
	 */
	public void setSpeed(double speed) {
		if (speed == 0)
		{
			this.speed = speed;
		}
		else
		{
			this.speed += speed;
			if (this.speed > 3) this.speed = 3;
			if (this.speed < -3) this.speed = -3;
		}
	}

	/**
	 * Réinitialise la vitesse du robot. 0.5f étant la vitesse par défaut.
	 */
	public void resetDefault() {
		this.setSpeed(0.5f);
	}
}
