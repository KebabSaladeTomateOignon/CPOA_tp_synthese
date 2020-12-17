package simbad.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simbad.sim.SimpleAgent;
import simbad.sim.Simulator;
import simbad.sim.World;
import tpsynthese.MyRobot;


public class AgentControlGUI extends JPanel implements ActionListener, ChangeListener
{
	private static final long serialVersionUID = 1L;
    World world; // L'attribut renseigne sur le monde 3D.
    Simulator simulator; // L'attribut renseigne sur le simulateur.
    AgentFollower agentFollower; // L'attribut renseigne sur l'agent Follower à déplacer.
    AgentFollower robotFollower; // L'attribut renseigne sur le robot Follower qui se déplace.
    Font smallFont; // L'attribut renseigne sur la police d'écriture.
    
    /**
     * Constructeur AgentControlGUI
     * @param world : Le monde 3D.
     * @param simulator : Le simulateur
     */
    public AgentControlGUI(World world, Simulator simulator) {
        this.world = world;
        smallFont = new Font("Arial",Font.PLAIN,11);
        createGUI();
        agentFollower = new AgentFollower(world, (SimpleAgent) simulator
                .getAgentList().get(0));
        robotFollower = new AgentFollower(world, (SimpleAgent) simulator
                .getAgentList().get(0));

    }
    /**
     * Initialise les touches ainsi que le layout.
     */
    void createGUI() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
        
        getInputMap(IFW).put(KeyStroke.getKeyStroke("Z"), MOVE_UP);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), MOVE_DOWN);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("D"), MOVE_RIGHT);
        getInputMap(IFW).put(KeyStroke.getKeyStroke("Q"), MOVE_LEFT);
        
        getActionMap().put(MOVE_UP, new MoveAction(MOVE_UP));
        getActionMap().put(MOVE_DOWN, new MoveAction(MOVE_DOWN));
        getActionMap().put(MOVE_RIGHT, new MoveAction(MOVE_RIGHT));
        getActionMap().put(MOVE_LEFT, new MoveAction(MOVE_LEFT));
        
    }

    public void actionPerformed(ActionEvent actionEvent) {
        
    }

    public void stateChanged(ChangeEvent changeEv) 
    { 
    	if (changeEv.getSource().equals('z')) {
    		((MyRobot)robotFollower.agent).setTranslationalVelocity(1);
			
		}
    }
	
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final String MOVE_UP     = "UP";
    private static final String MOVE_DOWN     = "DOWN";
    private static final String MOVE_RIGHT     = "RIGHT";
    private static final String MOVE_LEFT     = "LEFT";
   
    /**
     * Classe privée MoveAction
     * @author     : -
     * @version : 1.0
     * 
     */
    private class MoveAction extends AbstractAction 
    {
        private String nom; // l'action qui est réalisé (UP, DOWN, LEFT, RIGHT) 
        /**
         * Serial UID
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * Constructeur MoveAction
         * @param nom : Le nom de l'action
         */
        MoveAction(String nom) 
        {
            this.nom = nom;
        }    

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if ( AgentControlGUI.this.robotFollower != null && AgentControlGUI.this.world != null )
            {
            
                if ( this.nom != null && (! this.nom.equals("") ) )
                {
                
                    if ( this.nom.equals(MOVE_UP) )
                    {
                        ( (MyRobot) AgentControlGUI.this.robotFollower.agent).setSpeed(0.5f);
                    }
                    else if ( this.nom.equals(MOVE_DOWN) )
                    {
                    	( (MyRobot) AgentControlGUI.this.robotFollower.agent).setSpeed(-0.5f);
                    }
                    else if ( this.nom.equals(MOVE_RIGHT) )
                    {
                    	((MyRobot)robotFollower.agent).rotateY(-0.5);
                    }
                    else if ( this.nom.equals(MOVE_LEFT) )
                    {
                    	((MyRobot)robotFollower.agent).rotateY(0.5);
                    }
                }
            }
        }
    }
}

