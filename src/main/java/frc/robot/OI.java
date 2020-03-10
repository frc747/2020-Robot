package frc.robot;

import java.lang.SuppressWarnings;

import edu.wpi.first.wpilibj.*;
import frc.robot.commands.FlipIntakeArmsCommand;
import frc.robot.commands.PresetHood;
import frc.robot.commands.RotateVision;
import frc.robot.commands.SmartBallTransfer;
import frc.robot.input.Devices;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.interfaces.LimelightInterface.camMode;
import frc.robot.interfaces.LimelightInterface.ledMode;
public class OI {

  public static boolean shiftHigh = false;

  public static double distance;

  public static boolean functioningLidar = false;
  public static boolean functioningBeamBreak = false;
  public static boolean functioningPigeon = false;
  public static boolean functioningLimelight = false;
  public static boolean functioningHoodEncoder = false;
  public static boolean functioningServo = false;

  @SuppressWarnings("resource")
  public OI() {

    // example : Devices.operatorController.BUTTON_X.whenHeld(command, interruptible)
    // PLACE COMMANDS HERE
    Devices.xboxController.BUTTON_A.whileHeld(new RotateVision());
    Devices.xboxController.BUTTON_SELECT.whileHeld(new SmartBallTransfer());
    Devices.xboxController.BUTTON_B.whenPressed(new FlipIntakeArmsCommand());

    Devices.genesisController.BUTTON_A.toggleWhenPressed(new PresetHood(0));
    Devices.genesisController.BUTTON_B.toggleWhenPressed(new PresetHood(1));
    Devices.genesisController.BUTTON_C.toggleWhenPressed(new PresetHood(2));
    Devices.genesisController.BUTTON_X.toggleWhenPressed(new PresetHood(3));
    Devices.genesisController.BUTTON_Y.toggleWhenPressed(new PresetHood(4));
    Devices.genesisController.BUTTON_Z.toggleWhenPressed(new PresetHood(5));

    //Sensors.LoadCell.begin(10,11);

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

    SmartDashboard.putNumber("Right stick", Devices.xboxController.getRightY());

    SmartDashboard.putNumber("PIGEON ANGLE", Sensors.Pigeon.getAngle());
    SmartDashboard.putNumber("RAW PIGEON ANGLE", Sensors.Pigeon.getRawAngle());

    SmartDashboard.putNumber("SERVO ANGLE", Servos.limelightServo.getAngle());
    SmartDashboard.putNumber("Servo Value: ", Servos.limelightServo.get());

    SmartDashboard.putBoolean("Ball?", !Sensors.IRBreakBeam.getValue());

    Subsystems.LimelightServo.printOuts();
  }

  public static void checkSystems() {
    functioningLidar = Sensors.LIDAR.checkLidar();
    functioningLimelight = Sensors.Limelight.checkLimelight();
    functioningPigeon = Sensors.Pigeon.checkPigeon();
    functioningBeamBreak = Sensors.IRBreakBeam.checkBeamBreak();
    functioningServo = Subsystems.LimelightServo.checkServo();
    functioningHoodEncoder = Subsystems.Hood.checkHood();

    SmartDashboard.putBoolean("FUNCTIONING LIDAR: ", functioningLidar);
    SmartDashboard.putBoolean("FUNCTIONING LIMELIGHT: ", functioningLimelight);
    SmartDashboard.putBoolean("FUNCTIONING PIGEON: ", functioningPigeon);
    SmartDashboard.putBoolean("FUNCTIONING BEAM BREAK: ", functioningBeamBreak);
    SmartDashboard.putBoolean("FUNCTIONING SERVO: ", functioningServo);
    SmartDashboard.putBoolean("FUNCTIONING HOOD ENCODER: ", functioningHoodEncoder);
  }
}
