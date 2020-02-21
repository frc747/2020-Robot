package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;

// /import frc.robot.autonomous.*;

public class Autonomous{
    
    public enum AutoMode{
        AUTOMODE_EXAMPLE
    }
    
    private SendableChooser<AutoMode> autoChooser1;
    
    public Autonomous(){
        autoChooser1 = new SendableChooser<AutoMode>();
        
        autoChooser1.setDefaultOption("Example Auto", AutoMode.AUTOMODE_EXAMPLE);
        SmartDashboard.putData("Auto mode", autoChooser1);
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        AutoMode selectedAutoMode = (AutoMode)(autoChooser1.getSelected());
                    
        switch (selectedAutoMode){
            case AUTOMODE_EXAMPLE:
                Sensors.Limelight.setPipeline(1);
                //new ExampleAuto().schedule();
                break;
            default:
                break;
            }
    }
}