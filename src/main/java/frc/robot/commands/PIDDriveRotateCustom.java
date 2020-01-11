package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class PIDDriveRotateCustom extends CommandBase {

  double p = 3, i = 0, dAcute = 2.7, dObtuse = 2.7, output;

  double goal, threshold = 5;//2.5;

  double onTargetCount = 0;

  double lastError = 0, error, totalError = 0;

  double errorSlope;

  public PIDDriveRotateCustom(double angle, boolean resetGyro) {
    addRequirements(Robot.DRIVE_SUBSYSTEM);
    goal = angle;
    if (resetGyro) {
      Robot.resetPigeonAngle();
    }
  }

  public PIDDriveRotateCustom(double angle, boolean resetGyro, boolean closestEquivalent) {
    addRequirements(Robot.DRIVE_SUBSYSTEM);


    if (closestEquivalent)  {
    
      if (angle > 0) {

          goal = ((angle+180)%360)-180; //limits angle to range of -180 to 180

      } else {

        goal = ((angle-180)%360)+180; //limits angle to range of -180 to 180
        
      }
    } else {

    goal = angle;

    }

    if (resetGyro) {
      Robot.resetPigeonAngle();
    }
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    lastError = error;

    error = goal - Robot.getRawPigeonAngle();

    // DOESNT WORK WITH PIGEION; INTENDED FOR NAVX USE
    // if (Math.abs(lastError-error) > 180) {
    //   error = 0;
    // }

    
    if (goal < 90 && goal > -90) {
      errorSlope = ((lastError-error)/20)*-dAcute;
    } else {
      errorSlope = ((lastError-error)/20)*-dObtuse;
    }
    
    totalError = totalError + error;

    output = (Math.tanh(error/90)*p)+errorSlope+(totalError*i);

    Robot.DRIVE_SUBSYSTEM.set(output, -output);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    if(error < threshold && error > -threshold) {
      onTargetCount++;
    } else {
      onTargetCount = 0;
    }

    return onTargetCount > 5;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {

  }
}