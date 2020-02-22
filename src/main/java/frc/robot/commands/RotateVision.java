package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.Subsystems;

public class RotateVision extends CommandBase {
  
  double modifier = .5;

  public RotateVision() {
    addRequirements(Subsystems.Drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.limelightPivot = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double x = Sensors.Limelight.getHorizontalOffset();

    double left = -x/52.687;
    double right = x/52.687;
    Subsystems.Drive.set(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.limelightPivot = false;
    Subsystems.Drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
