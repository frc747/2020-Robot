package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.autonomous.TestAuto;
import frc.robot.autonomous.TrenchAuto;

/**
 * This class is for creation  of the autonomous selector in
 * SmartDashboard, as well as the assignment of what commands
 * the various selections actually run
 */
public class Autonomous{
    
    public enum AutoMode{
        AUTOMODE_EXAMPLE,
        TRENCH_AUTO,
        TEST_AUTO;
    }
    
    private SendableChooser<AutoMode> autoChooser;
    
    /**
     * Constructor creates autonomous chooser and adds options chosen from 
     * AutoMode enumerator
     */
    public Autonomous(){
        autoChooser = new SendableChooser<AutoMode>();
        
        autoChooser.addOption("Example Auto", AutoMode.AUTOMODE_EXAMPLE);
        autoChooser.setDefaultOption("Trench Auto", AutoMode.TRENCH_AUTO);
        autoChooser.addOption("Test Auto", AutoMode.TEST_AUTO);
        SmartDashboard.putData("Auto mode", autoChooser);
    }
    
    /**
     * This function schedules an autonomous command/command group
     * based on the currently chosen autonomous routine
     */
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        AutoMode selectedAutoMode = (AutoMode)(autoChooser.getSelected());
                    
        switch (selectedAutoMode){
            case AUTOMODE_EXAMPLE:
                Sensors.Limelight.setPipeline(1);
                //new ExampleAuto().schedule();
                break;
            case TRENCH_AUTO:
                new TrenchAuto().schedule();
                break;
            case TEST_AUTO:
                new TestAuto().schedule();
                break;
            default:
                break;
            }
    }
}