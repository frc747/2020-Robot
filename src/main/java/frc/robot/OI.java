package frc.robot;

import java.lang.SuppressWarnings;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.interfaces.LimelightInterface.camMode;
import frc.robot.interfaces.LimelightInterface.ledMode;
public class OI {

  public static boolean shiftHigh = false;

  public static boolean tongueIsOut = false;

  public static boolean latchInPosition = false;

  public static double distance;

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
    B_BUTTON.whileHeld(new RunTransfer());
    
    START_BUTTON.whenPressed(new PIDDriveRotateCustom(Sensors.Limelight.getHorizontalOffset(), true));
    Y_BUTTON.whileHeld(new LimelightPreviewCommand());

    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
    // OI.table.getEntry("stream").setDouble(0);


  }

  // Anything to be updated should be done in here
  public void updateOI() {
    
    // Limelight Value SmartDashboard display
        
    SmartDashboard.putNumber("LEFT POS: ", Subsystems.Drive.getLeftPosition());
    SmartDashboard.putNumber("RIGHT POS: ", Subsystems.Drive.getRightPosition());

    if (Subsystems.Drive.tracking) {
      Sensors.Limelight.setCamMode(camMode.VISION_PROCESSOR);
      Sensors.Limelight.setLEDMode(ledMode.ON);
    } else {
      Sensors.Limelight.setCamMode(camMode.DRIVER_CAMERA);
      Sensors.Limelight.setLEDMode(ledMode.OFF);
    }
    
    SmartDashboard.putNumber("Joystick Left", leftStick.getRawAxis(1));
    SmartDashboard.putNumber("Joystick Right", rightStick.getRawAxis(1));

    SmartDashboard.putNumber("PIGEON ANGLE", Sensors.Pigeon.getAngle());
    SmartDashboard.putNumber("RAW PIGEON ANGLE", Sensors.Pigeon.getRawAngle());
  }
}
