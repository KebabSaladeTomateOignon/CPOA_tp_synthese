package tpsynthese;

import simbad.gui.Simbad;

/**
 * Classe PortalLike : Contrôleur
 * 
 * @author : Portal
 * @version : 1.0 
 * date : 17/12/2020
 */

public class PortalLike 
{
	private static final String NOM_NIVEAU 	= "niveau0"; 
	// L'attribut renseigne la structure du fichier txt. 
	private static final int 	NB_NIVEAU 	= 2;
	// L'attribut renseigne le nombre de niveau. 
	private static int 			NUM_NIVEAU 	= 1;
	// L'attribut renseigne sur le numéro du niveau. 

	public static void main(String[] args)
	{
		MyEnv env = new MyEnv( PortalLike.NOM_NIVEAU + PortalLike.NUM_NIVEAU);
		Simbad frame = new Simbad(env, false);
		
		while( PortalLike.NUM_NIVEAU-1 < PortalLike.NB_NIVEAU )
		{
			// Si la sumulation a commenc� et que le timer n'a pas commencé
			if ( frame.getStarted() && ( ! frame.getTimerWindow().getTimerRunning() ))
			{
				frame.getTimerWindow().timerStart();
			}
			else if ( ( ! frame.getStarted()) && ( frame.getTimerWindow().getTimerRunning() ) )
			{
				frame.getTimerWindow().timerStop();
			}
			
			// Si le joueur a gagné ou que le temps est écoulé
			if ( env.getGagne() || frame.isFinished() )
			{
				if ( env.getGagne() )
					PortalLike.NUM_NIVEAU++;
				
				if ( frame.isFinished() && (! frame.retryGame()) )
				{
					frame.quitGame();
					break;
				}
				
				frame.closeTimer();
				frame.resetTimer();
				if(PortalLike.NUM_NIVEAU <= PortalLike.NB_NIVEAU )
				{
					env = new MyEnv( PortalLike.NOM_NIVEAU + PortalLike.NUM_NIVEAU);
					frame.start(env);
				}
			}	
		}
		frame.winGame();

		
	}
}
