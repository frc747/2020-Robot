package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Motors;
import frc.robot.Subsystems;
import frc.robot.input.Devices;

public class TankDriveCommand extends CommandBase {

  int timeoutMs = 10;

  double shiftCount = 0;

  private static final double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public TankDriveCommand() {
    addRequirements(Subsystems.Drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    Motors.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Motors.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Motors.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Motors.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    Motors.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Motors.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Motors.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Motors.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double speed = 1;

    /*
    Rough deadband
    
    double left = -Devices.leftStick.getY();
    double right = -Devices.rightStick.getY();
    if (Math.abs(left) < 0.1) {
        left = 0;
    }
    if (Math.abs(right) < 0.1) {
        right = 0;
    }
    Subsystems.Drive.set(left * speed, right * speed);
    */

    double leftValue = applyDeadband(Devices.leftStick.getY(), 0.15);
    double rightValue = applyDeadband(Devices.rightStick.getY(), 0.15);
    Subsystems.Drive.set(leftValue * speed, rightValue * speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    
  }

  private double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }
}
