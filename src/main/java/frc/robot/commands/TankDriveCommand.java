package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Robot;

public class TankDriveCommand extends CommandBase {

  int timeoutMs = 10;

  double shiftCount = 0;

  private static final double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public TankDriveCommand() {
    addRequirements(Robot.DRIVE_SUBSYSTEM);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    /*double left = -OI.leftStick.getRawAxis(1);
    double right = -OI.rightStick.getRawAxis(1);*/
    double left = OI.rightStick.getRawAxis(1);
    double right = OI.leftStick.getRawAxis(1);

    if (Math.abs(left) < 0.1) {
        left = 0;
    }
    if (Math.abs(right) < 0.1) {
        right = 0;
    }
    
    double speed = 1;
    
    Robot.DRIVE_SUBSYSTEM.set(left * speed, right * speed);
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
}
