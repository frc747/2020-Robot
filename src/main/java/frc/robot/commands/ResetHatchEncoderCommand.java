package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ResetHatchEncoderCommand extends CommandBase {
  
  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;
  
  public ResetHatchEncoderCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    Robot.HATCH_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    Robot.HATCH_SUBSYSTEM.hatchTalon.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    return true;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    
  }
}
