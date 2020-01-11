package frc.robot;

import java.lang.SuppressWarnings;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

public class OI {

  public static boolean shiftHigh = false;

  public static boolean tongueIsOut = false;

  public static boolean latchInPosition = false;

  public static double distance;

  public static NetworkTable table;
  public static double v;
  public static double x;
  public static double y;
  public static double area;

  public static Joystick leftStick = new Joystick(RobotMap.Controller.LEFT_STICK.getValue());
  public static Joystick rightStick = new Joystick(RobotMap.Controller.RIGHT_STICK.getValue());
  public static Joystick operatorController = new Joystick(RobotMap.Controller.OPERATOR_CONTROLLER.getValue());

  //commented out testController joystick
  // public static Joystick testController = new Joystick(3);

  //commented out testController joystickbuttons
  // Button Y_BUTTON_TEST = new JoystickButton(testController, 4);
  // Button B_BUTTON_TEST = new JoystickButton(testController, 2);
  // Button A_BUTTON_TEST = new JoystickButton(testController, 1);
  // Button LEFT_BUMPER_TEST = new JoystickButton(testController, 5);

  Button B_BUTTON = new JoystickButton(operatorController, 2);
  Button A_BUTTON = new JoystickButton(operatorController, 1);
  Button Y_BUTTON = new JoystickButton(operatorController, 4);
  Button X_BUTTON = new JoystickButton(operatorController, 3);
  Button SELECT_BUTTON = new JoystickButton(operatorController, 7);
  Button START_BUTTON = new JoystickButton(operatorController, 8);
  Button LEFT_STICK_TRIG = new JoystickButton(leftStick, 1);




  // Button LEFT_STICK_BUTTON_SEVEN = new JoystickButton(leftStick, 7);

  @SuppressWarnings("resource")
  public OI() {

    LEFT_STICK_TRIG.toggleWhenPressed(new PIDDriveInches(100, false));

    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
    // OI.table.getEntry("stream").setDouble(0);

  }

  // Anything to be updated should be done in here
  public void updateOI() {
    
    // Limelight Value SmartDashboard display
    table = NetworkTableInstance.getDefault().getTable("limelight");
        
    SmartDashboard.putNumber("LEFT POS: ", Robot.DRIVE_SUBSYSTEM.getLeftPosition());
    SmartDashboard.putNumber("RIGHT POS: ", Robot.DRIVE_SUBSYSTEM.getRightPosition());


    v = table.getEntry("tv").getDouble(0);

    x = table.getEntry("tx").getDouble(0);
    y = table.getEntry("ty").getDouble(0);
    area = table.getEntry("ta").getDouble(0);

    if (Robot.DRIVE_SUBSYSTEM.tracking) {
      OI.table.getEntry("camMode").setDouble(0);
      OI.table.getEntry("ledMode").setDouble(3);
    } else {
      OI.table.getEntry("camMode").setDouble(1);
      OI.table.getEntry("ledMode").setDouble(1);
    }
    
    SmartDashboard.putNumber("Joystick Left", leftStick.getRawAxis(1));
    SmartDashboard.putNumber("Joystick Right", rightStick.getRawAxis(1));

    SmartDashboard.putNumber("PIGEON ANGLE", Robot.getPigeonAngle());
    SmartDashboard.putNumber("RAW PIGEON ANGLE", Robot.getRawPigeonAngle());
  }
}
