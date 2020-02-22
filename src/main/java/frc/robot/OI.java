package frc.robot;

import java.lang.SuppressWarnings;

import edu.wpi.first.wpilibj.*;
import frc.robot.commands.RotateVision;
import frc.robot.input.Devices;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.interfaces.LimelightInterface.camMode;
import frc.robot.interfaces.LimelightInterface.ledMode;
import frc.robot.subsystems.LimelightServoSubsystem;
public class OI {

  public static boolean shiftHigh = false;

  public static double distance;

  @SuppressWarnings("resource")
  public OI() {

    // example : Devices.operatorController.BUTTON_X.whenHeld(command, interruptible)

    Devices.operatorController.BUTTON_A.whileHeld(new RotateVision());

    // Ignore this error, no known conflict
    new Notifier(() -> updateOI()).startPeriodic(.1);
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
    
    SmartDashboard.putNumber("Joystick Left", Devices.leftStick.getY());
    SmartDashboard.putNumber("Joystick Right", Devices.rightStick.getY());

    SmartDashboard.putNumber("Right stick", Devices.operatorController.getRightY());

    SmartDashboard.putNumber("PIGEON ANGLE", Sensors.Pigeon.getAngle());
    SmartDashboard.putNumber("RAW PIGEON ANGLE", Sensors.Pigeon.getRawAngle());

    SmartDashboard.putNumber("SERVO ANGLE", Servos.limelightServo.getAngle());
    SmartDashboard.putNumber("Servo Value: ", Servos.limelightServo.get());

    SmartDashboard.putBoolean("Ball?", !Sensors.IRBreakBeam.getValue());

    Subsystems.LimelightServo.printOuts();
  }
}
