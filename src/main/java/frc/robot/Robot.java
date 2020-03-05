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
import frc.robot.input.Devices;
//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;
  public static boolean limelightPivot = false;
  public static boolean under35 = false;
  private Command autonomousCommand;
  public Autonomous autonomous;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  public static Preferences prefs;

  @Override
  public void robotInit() {

    Subsystems.setDefaultCommands();

    Sensors.LIDAR.startMeasuring();
    Subsystems.Drive.tracking = true;

    prefs = Preferences.getInstance();
    Subsystems.Drive.resetBothEncoders();

    // UsbCamera ucamera = CameraServer.getInstance().startAutomaticCapture("cam1",
    // 0);
    // ucamera.setResolution(180, 240);

    this.autonomous = new Autonomous();

    if (m_oi == null) {
      m_oi = new OI();
    }

    /*
     * shooterThirteen.configPeakCurrentLimit(45);
     * shooterThirteen.configPeakOutputForward(1);
     * shooterThirteen.configPeakOutputReverse(1);
     */
  }

  /**
   * e This function is called every robot packet, no matter the mode. Use this
   * for items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //SmartDashboard.putNumber("Transfer Pos", Motors.transfer.getSelectedSensorPosition());
    /*if (Sensors.Limelight.validTargets()) {
      SmartDashboard.putNumber("Distance (trig): ", 1.16545 * ( (83-13.5) / Math.tan(Math.toRadians(12.5+Sensors.Limelight.getVerticalOffset()))) -12.1941) ;
      SmartDashboard.putNumber("Distance (trig original): ", ( (83-13.5) / Math.tan(Math.toRadians(12.5+Sensors.Limelight.getVerticalOffset())))) ;
      
      SmartDashboard.putNumber("Distance (area):", 194.1278201032424 * Math.sqrt(Sensors.Limelight.getArea()));
    }*/

    SmartDashboard.putNumber("LIDAR Distance", Sensors.LIDAR.getDistance());

    SmartDashboard.putNumber("Left Intake Arm", Motors.leftIntakeArm.getSelectedSensorPosition());
    SmartDashboard.putNumber("Right Intake Arm", Motors.rightIntakeArm.getSelectedSensorPosition());

  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.Q
   */
  @Override
  public void disabledInit() {
    Subsystems.Drive.changeDriveBrakeMode(false);
  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();

    if (Devices.xboxController.BUTTON_A.get()) {
      Subsystems.Drive.tracking = true;
    } else {
      Subsystems.Drive.tracking = false;
    }
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
    Sensors.Pigeon.resetAngle();
    Subsystems.Drive.changeDriveBrakeMode(true);
    // this is now done within the autonomous command groups (within initialize)

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
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    Sensors.Limelight.setPipeline(0);
    Sensors.Pigeon.resetAngle();
    Subsystems.Drive.resetBothEncoders();
    Subsystems.Drive.changeDriveBrakeMode(true/*true*/);
    Motors.shooter.setNeutralMode(NeutralMode.Coast);

    Motors.shooter.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    
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

    SmartDashboard.putBoolean("IR Sensor: ", !Sensors.IRBreakBeam.getValue());

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
