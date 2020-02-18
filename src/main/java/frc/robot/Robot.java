/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.commands.ShiftDriveCommand;
//import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.playingwithfusion.TimeOfFlight;

import java.util.concurrent.TimeoutException;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.sensors.PigeonIMU;
import frc.robot.commands.HoodToAngle;
import frc.robot.commands.ShooterStick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static boolean climbBrakeMode;
  public static DriveSubsystem DRIVE_SUBSYSTEM = new DriveSubsystem();
  public static OI m_oi;

  public static boolean latchInPos = false;

  public static boolean operatorControl = false;
  public static boolean isAutonomous = false;
  public static boolean isTeleop = false;

  public static boolean autoSideLeft = false;
  public static boolean autoSideRight = false;
  public static boolean autoSideFaceCargoShip = false;
  public static boolean autoFrontFaceCargoShip = false;
  public static boolean autoRocket = false;

  private double transferPos;
  private int count;

  public static String side = "";
 
	private Command autonomousCommand;
  public Autonomous autonomous;

  public TalonSRX indexerFour = new TalonSRX(4);
  public TalonSRX intakeNine = new TalonSRX(9);
  public TalonSRX shooterThirteen = new TalonSRX(13);
  public TalonSRX transferTwo = new TalonSRX(2);

  DigitalInput IRBreakBeam = new DigitalInput(0);

  TimeOfFlight TOFSensor = new TimeOfFlight(0);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  
  public static final PigeonIMU PIGEON = new PigeonIMU(0);

  private static double[] ypr = new double[3];

  public static Preferences prefs;

  public static double getPigeonAngle() {
    PIGEON.getYawPitchRoll(ypr);

    if (-ypr[0] > 0) {
      return ((-ypr[0]+180)%360)-180; //limits angle to range of -180 to 180
    } else {
      return ((-ypr[0]-180)%360)+180; //limits angle to range of -180 to 180
    }
  }
  
  public static double getRawPigeonAngle() {
    PIGEON.getYawPitchRoll(ypr); 
    return -ypr[0];     //returns exact pigeon angle without range limiting
  }

  public static double getPigeonAngleRadians() {
    return Math.toRadians(getPigeonAngle());
  }
  
  public static double getRawPigeonAngleRadians() {
    return Math.toRadians(getRawPigeonAngle());
  }

  public static void resetPigeonAngle() {
    PIGEON.setYaw(0);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void robotInit() {
    
    OI.SHOOTER_SUBSYSTEM.setDefaultCommand(new ShooterStick(OI.SHOOTER_SUBSYSTEM));
    OI.HOOD_SUBSYSTEM.setDefaultCommand(new HoodToAngle());//OI.LIDAR_SUBSYSTEM.getDistance(), OI.HOOD_SUBSYSTEM));
    DRIVE_SUBSYSTEM.setDefaultCommand(new ShiftDriveCommand());

    OI.LIDAR_SUBSYSTEM.startMeasuring();

    prefs = Preferences.getInstance();
    DRIVE_SUBSYSTEM.resetBothEncoders();

    //UsbCamera ucamera = CameraServer.getInstance().startAutomaticCapture("cam1", 0);
    //ucamera.setResolution(180, 240);

    SmartDashboard.putNumber("SET RPM : ", 0);

    
    this.autonomous = new Autonomous();

    if(m_oi == null) {
      m_oi = new OI();
    }

    /*shooterThirteen.configPeakCurrentLimit(45);
    shooterThirteen.configPeakOutputForward(1);
    shooterThirteen.configPeakOutputReverse(1);*/
  }

  /**e
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Transfer Pos", transferTwo.getSelectedSensorPosition());
    if (LimelightSubsystem.validTargets()) {
      SmartDashboard.putNumber("Distance (trig): ", 1.16545 * ( (83-13.5) / Math.tan(Math.toRadians(12.5+LimelightSubsystem.getVerticalOffset()))) -12.1941) ;
      SmartDashboard.putNumber("Distance (trig original): ", ( (83-13.5) / Math.tan(Math.toRadians(12.5+LimelightSubsystem.getVerticalOffset())))) ;
      
      SmartDashboard.putNumber("Distance (area):", 194.1278201032424 * Math.sqrt(LimelightSubsystem.getArea()));
    }

    SmartDashboard.putNumber("Shooter", OI.operatorController.getRawAxis(5));


    SmartDashboard.putNumber("DISTANCE VIA TOF", TOFSensor.getRange());

    SmartDashboard.putNumber("LIDAR Distance", OI.LIDAR_SUBSYSTEM.getDistance());

  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.Q
   */
  @Override
  public void disabledInit() {
    DRIVE_SUBSYSTEM.changeDriveBrakeMode(false);
    operatorControl = false;
    isAutonomous = false;
    isTeleop = false;
  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    resetPigeonAngle();
    DRIVE_SUBSYSTEM.changeDriveBrakeMode(true);
    // this is now done within the autonomous command groups (within initialize)
    // operatorControl = false;
    isAutonomous = true;
    isTeleop = false;

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
      autonomous.startMode();
      if (autonomousCommand != null) {
          autonomousCommand.schedule();
      }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if (DriverStation.getInstance().getMatchTime() < 8.75 && DriverStation.getInstance().getMatchTime() > 0) {
      if (side.compareTo("rightTwo") == 0) {
        OI.table.getEntry("pipeline").setDouble(1.0);
      } else if (side.compareTo("leftTwo") == 0) {
        OI.table.getEntry("pipeline").setDouble(2.0);
      } else if (side.compareTo("left") == 0 || side.compareTo("right") == 0) {
        OI.table.getEntry("pipeline").setDouble(0.0);
      }
    }
    
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    OI.table.getEntry("pipeline").setDouble(0.0);
    resetPigeonAngle();
    DRIVE_SUBSYSTEM.resetBothEncoders();
    DRIVE_SUBSYSTEM.changeDriveBrakeMode(false/*true*/);
    operatorControl = true;
    shooterThirteen.setNeutralMode(NeutralMode.Coast);
    isAutonomous = false;
    isTeleop = true;

    shooterThirteen.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();

    //shooterThirteen.set(ControlMode.PercentOutput, OI.operatorController.getRawAxis(1)*(RPM/6380.0));
    
    /*if (OI.operatorController.getRawButton(2)) {
      indexerFour.set(ControlMode.PercentOutput, -0.75);
    } else {
      indexerFour.set(ControlMode.PercentOutput, 0);
    }

    if (OI.operatorController.getRawButton(1)) {
      transferTwo.set(ControlMode.PercentOutput, -1.0);
    } else {
      transferTwo.set(ControlMode.PercentOutput, 0.0);
    }

    if (OI.operatorController.getRawButton(4)) {
      intakeNine.set(ControlMode.PercentOutput, -0.50);
    } else {
      intakeNine.set(ControlMode.PercentOutput, 0);
    }*/

    /*if (OI.operatorController.getRawButton(1)) {
      shooterThirteen.set(ControlMode.PercentOutput, SHOOTER_PERCENT);
    } else {
      shooterThirteen.set(ControlMode.PercentOutput, 0.0);

    }*/

    SmartDashboard.putNumber("indexer", OI.operatorController.getRawAxis(1));

    //SmartDashboard.putNumber("ACTUAL RPM", shooterThirteen.getSelectedSensorVelocity()*600.0/2048.0);
    SmartDashboard.putNumber("Current Draw", shooterThirteen.getStatorCurrent());

    SmartDashboard.putBoolean("IR Sensor: ", !IRBreakBeam.get());

    /*if (IRBreakBeam.get()) {
      //count = 0;
      transferTwo.set(ControlMode.PercentOutput, -1.0);
    } else {
      /*count++;
      if (count == 1) {
        transferPos = transferTwo.getSelectedSensorPosition();
      }
      if (count > 0) {
        transferTwo.set(ControlMode.PercentOutput, Math.tanh(transferTwo.getSelectedSensorPosition()-transferPos));
      }
      transferTwo.set(ControlMode.PercentOutput, 1.0);
    }*/

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
