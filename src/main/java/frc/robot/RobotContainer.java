package frc.robot;

import java.lang.SuppressWarnings;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.maps.Constants;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PigeonSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotContainer {

  public static boolean shiftHigh = false;

  public static double distance;

  public static Joystick leftStick = new Joystick(Constants.Controller.LEFT_STICK.getValue());
  public static Joystick rightStick = new Joystick(Constants.Controller.RIGHT_STICK.getValue());
  public static Joystick operatorController = new Joystick(Constants.Controller.OPERATOR_CONTROLLER.getValue());

  Button B_BUTTON = new JoystickButton(operatorController, 2);
  Button A_BUTTON = new JoystickButton(operatorController, 1);
  Button Y_BUTTON = new JoystickButton(operatorController, 4);
  Button X_BUTTON = new JoystickButton(operatorController, 3);
  Button SELECT_BUTTON = new JoystickButton(operatorController, 7);
  Button START_BUTTON = new JoystickButton(operatorController, 8);
  Button LEFT_STICK_TRIG = new JoystickButton(leftStick, 1);

  @SuppressWarnings("resource")
  public RobotContainer() {

    configureButtonBindings();

    Robot.m_driveSubsystem.setDefaultCommand(new ShiftDriveCommand());

  }

  public void configureButtonBindings() {
    LEFT_STICK_TRIG.toggleWhenPressed(new PIDDriveInches(100, false));
  }

  // Anything to be updated should be done in here
  public void update() {
    
    System.out.println(Robot.m_driveSubsystem.leftDrivePrimary.getSelectedSensorVelocity());
    //System.out.println(Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.getSelectedSensorPosition());
    // Limelight Value SmartDashboard display

    SmartDashboard.putNumber("LEFT POS: ", Robot.m_driveSubsystem.undoGearRatio(Robot.m_driveSubsystem.getLeftPosition()));
    SmartDashboard.putNumber("RIGHT POS: ", Robot.m_driveSubsystem.undoGearRatio(Robot.m_driveSubsystem.getRightPosition()));

    SmartDashboard.putNumber("X Odometry Position", Robot.m_driveSubsystem.getPose().getTranslation().getX()*39.3701);
    SmartDashboard.putNumber("Y Odometry Position", Robot.m_driveSubsystem.getPose().getTranslation().getY()*39.3701);

    if (LimelightSubsystem.tracking) {
      LimelightSubsystem.setCamMode(LimelightSubsystem.camMode.VISION_PROCESSOR);
      LimelightSubsystem.setLEDMode(LimelightSubsystem.ledMode.ON);
    } else {
      LimelightSubsystem.setCamMode(LimelightSubsystem.camMode.DRIVER_CAMERA);
      LimelightSubsystem.setLEDMode(LimelightSubsystem.ledMode.OFF);
    }
    
    SmartDashboard.putNumber("Joystick Left", leftStick.getRawAxis(1));
    SmartDashboard.putNumber("Joystick Right", rightStick.getRawAxis(1));

    SmartDashboard.putNumber("PIGEON ANGLE", PigeonSubsystem.getPigeonAngle());
    SmartDashboard.putNumber("RAW PIGEON ANGLE", PigeonSubsystem.getRawPigeonAngle());
  }
}
