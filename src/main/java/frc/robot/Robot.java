/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PigeonSubsystem;
//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.DriverStation;

//import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static RobotContainer m_robotContainer;

  public static DriveSubsystem m_driveSubsystem = new DriveSubsystem();

  public static boolean operatorControl = false;
  public static boolean isAutonomous = false;
  public static boolean isTeleop = false;

  public static boolean autoSideLeft = false;
  public static boolean autoSideRight = false;

  public static String side = "";
 
  public static Autonomous autonomous;

  public static Preferences prefs;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  @Override
  public void robotInit() {
    prefs = Preferences.getInstance();
    m_driveSubsystem.resetBothEncoders();

    //UsbCamera ucamera = CameraServer.getInstance().startAutomaticCapture("cam1", 0);
    //ucamera.setResolution(180, 240);

    autonomous = new Autonomous();

    m_driveSubsystem.resetOdometry(new Pose2d());

    m_robotContainer = new RobotContainer();

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
    CommandScheduler.getInstance().run();
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    
    m_robotContainer.update();
    
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.Q
   */
  @Override
  public void disabledInit() {
    m_driveSubsystem.changeDriveBrakeMode(false);
    operatorControl = false;
    isAutonomous = false;
    isTeleop = false;
  }

  @Override
  public void disabledPeriodic() {

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
    PigeonSubsystem.resetPigeonAngle();
    m_driveSubsystem.changeDriveBrakeMode(true);
    // this is now done within the autonomous command groups (within initialize)
    // operatorControl = false;
    isAutonomous = true;
    isTeleop = false;

    autonomous.startMode();
  }


  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    if (DriverStation.getInstance().getMatchTime() < 8.75 && DriverStation.getInstance().getMatchTime() > 0) {
      if (side.compareTo("rightTwo") == 0) {
        LimelightSubsystem.setPipeline(1);
      } else if (side.compareTo("leftTwo") == 0) {
        LimelightSubsystem.setPipeline(2);
      } else if (side.compareTo("left") == 0 || side.compareTo("right") == 0) {
        LimelightSubsystem.setPipeline(0);
      }
    }
  }

  @Override
  public void teleopInit() {
    LimelightSubsystem.setPipeline(0);
    PigeonSubsystem.resetPigeonAngle();
    m_driveSubsystem.resetBothEncoders();
    m_driveSubsystem.resetOdometry(new Pose2d());
    m_driveSubsystem.changeDriveBrakeMode(true);
    operatorControl = true;
    isAutonomous = false;
    isTeleop = true;
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }

}
