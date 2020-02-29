package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.Subsystems;
import frc.robot.interfaces.LimelightInterface.camMode;

public class RotateVision extends CommandBase {
  
  double modifier = .5;

  public RotateVision() {
    // addRequirements(Subsystems.Drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.limelightPivot = true;
    // Sensors.Limelight.setPipeline(0);
    Subsystems.Drive.tracking = true;
    // Sensors.Limelight.setCamMode(camMode.VISION_PROCESSOR);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double x = Sensors.Limelight.getHorizontalOffset();

    double left = -x/65;
    double right = x/65;
    // Subsystems.Drive.set(left, right);
    // Subsystems.Drive.stop();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Sensors.Limelight.setPipeline(1);
    Subsystems.Drive.tracking = false;
    // Sensors.Limelight.setCamMode(camMode.DRIVER_CAMERA);
    Robot.limelightPivot = false;
    Subsystems.Drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
