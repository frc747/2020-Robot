package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Robot;

public class TankDriveCommand extends CommandBase {

  int timeoutMs = 10;

  double shiftCount = 0;

  private static final double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  public TankDriveCommand() {
    addRequirements(Robot.m_driveSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    Robot.m_driveSubsystem.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.m_driveSubsystem.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.m_driveSubsystem.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.m_driveSubsystem.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.m_driveSubsystem.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.m_driveSubsystem.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.m_driveSubsystem.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.m_driveSubsystem.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double left = -RobotContainer.leftStick.getRawAxis(1);
    double right = -RobotContainer.rightStick.getRawAxis(1);

    if (Math.abs(left) < 0.1) {
        left = 0;
    }
    if (Math.abs(right) < 0.1) {
        right = 0;
    }
    
    double speed = 1;
    
    Robot.m_driveSubsystem.set(left * speed, right * speed);
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
