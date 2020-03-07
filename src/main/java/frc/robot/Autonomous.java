package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.autonomous.IntakeAuto;
import frc.robot.autonomous.TrenchAuto;

// /import frc.robot.autonomous.*;

public class Autonomous{
    
    public enum AutoMode{
        AUTOMODE_EXAMPLE,
        TRENCH_AUTO;
    }
    
    private SendableChooser<AutoMode> autoChooser1;
    
    public Autonomous(){
        autoChooser1 = new SendableChooser<AutoMode>();
        
        autoChooser1.setDefaultOption("Example Auto", AutoMode.AUTOMODE_EXAMPLE);
        autoChooser1.setDefaultOption("Trench Auto", AutoMode.TRENCH_AUTO);
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
            case TRENCH_AUTO:
                new TrenchAuto().schedule();
                break;
            default:
                break;
            }
    }
}