package simbad.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;



/**
 * This window is used to visualise the Timer.
 */
public final class TimerWindow extends JInternalFrame {
  
	private static final long serialVersionUID = 1L;
	
	private static Timer timer;
	private JLabel lblTimer;
	private int sec;
	
    public TimerWindow(int sec) {
        super("Timer");
        this.sec = sec;
        initialize();
    }

    private void initialize() {
    	
    	JLabel lblTxt = new JLabel();
    	JLabel lblTimer = new JLabel("Respecter le temps pour gagner");
    	lblTxt.setFont( new Font("Calibri", Font.TYPE1_FONT, 15) );
    	lblTimer.setFont( new Font("Calibri", Font.TYPE1_FONT, 15) );
    	
    	TimerWindow.timer = new Timer(1000, new ActionListener()
    			{
    				public void actionPerformed(ActionEvent e)
    				{
    					if ( sec <= 0 )
    					{
    						timer.stop();
    					}
    					else
    					{
    						if ( sec <= 10 )
    						{
    							if ( sec%2==0 )
    								lblTimer.setForeground(Color.RED);
    							else
    								lblTimer.setForeground(Color.BLACK);
    						}

    						sec--;
    						lblTxt.setText("Temps restant : ");
    						lblTimer.setText(timeConv(sec));
    					}
    				}
    			}
    			);
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(lblTxt, BorderLayout.WEST );
        panel.add(lblTimer, BorderLayout.CENTER);
        setContentPane(panel);
        this.pack();
        setResizable(true);
    }
    
    public void resetSecondes()
    {
    	this.timerStop();
    	this.sec = 0;
    }
    
    public boolean isFinished()
    {
    	return this.sec <= 0;
    }
    
    public boolean getTimerRunning()
    {
    	return this.timer.isRunning();
    }
    
    public void timerStart()
    {
    	if ( ! timer.isRunning() )
    		timer.start();
    }
    
    public void timerStop()
    {
    	if ( timer.isRunning() )
    		timer.stop();
    }
    
	private String timeConv(int seconds) 
	{
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = seconds % 60;

		return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
	}

	private String twoDigitString(int number) 
	{
		if (number == 0) 
		{
			return "00";
		}

		if (number / 10 == 0) {
			return "0" + number;
		}

		return String.valueOf(number);
	}
}