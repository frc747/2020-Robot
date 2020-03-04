package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.Subsystems;
import frc.robot.interfaces.LimelightInterface.camMode;

public class RotateVision extends CommandBase {
  
  double modifier = .5;
  double left;
  double right;
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
    
    if (Math.abs(x) > 12) {
      left = (-x/50) - .15;
      right = (x/50) + .15;
    } else if (Math.abs(x) > 2) {
      if(x > 0) {
        left = -.15;
        right = .15;
      } else {
        left = .15;
        right = -.15;
      }
    } else {
      left = 0;
      right = 0;
    }
    
    // double left = Math.atan(-x)/10;
    // double right = Math.atan(x)/10;
    SmartDashboard.putNumber("X", x);
    SmartDashboard.putNumber("left", left);
    SmartDashboard.putNumber("right", right);
    Subsystems.Drive.set(left, right);
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
