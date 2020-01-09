package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.brownies.*;

public class Autonomous{
    
    public enum AutoMode{
        AUTOMODE_BROWNIES
    }
    
    private SendableChooser<AutoMode> autoChooser1;
    
    public Autonomous(){
        autoChooser1 = new SendableChooser<AutoMode>();
        
        autoChooser1.setDefaultOption("Brownies", AutoMode.AUTOMODE_BROWNIES);
        SmartDashboard.putData("Auto mode", autoChooser1);
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    
        AutoMode selectedAutoMode = (AutoMode)(autoChooser1.getSelected());
                    
        switch (selectedAutoMode){
            case AUTOMODE_BROWNIES:
                new First().schedule(); // girls name here
                break; 
            default:
                break;
            }
    }
}