package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Sensors;
import frc.robot.Subsystems;

public class RotationalLockMode extends CommandBase {

  double p = 1, i = 0, dAcute = .2, dObtuse = .2, output ;

  double goal = 0, threshold = 5;//2.5;

  double onTargetCount = 0;

  double lastError = 0, error, totalError = 0;

  double errorSlope;

  public RotationalLockMode() {
    addRequirements(Subsystems.Drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    Sensors.Pigeon.resetAngle();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    lastError = error;

    error = goal - Sensors.Pigeon.getAngle();

    if (Math.abs(lastError-error) > 180) {
      error = 0;
    }
    if (goal < 90 && goal > -90) {
      errorSlope = ((lastError-error)/20)*-dAcute;
    } else {
      errorSlope = ((lastError-error)/20)*-dObtuse;
    }
    
    totalError = totalError + error;

    output = (Math.tanh(error/90)*p)+errorSlope+(totalError*i);

    Subsystems.Drive.set(output, -output);
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