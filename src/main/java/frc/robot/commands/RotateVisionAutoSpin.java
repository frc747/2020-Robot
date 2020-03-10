package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.Subsystems;

public class RotateVisionAuto extends CommandBase {
  
  double modifier = .5;
  double left;
  double right;

  double offset;

  public RotateVisionAuto() {
    addRequirements(Subsystems.Drive);
    offset = 0;
  }

  public RotateVisionAuto(double x_offset) {
    addRequirements(Subsystems.Drive);
    offset = x_offset;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.limelightPivot = true;
    Robot.teleopPivot = false;
    // Sensors.Limelight.setPipeline(0);
    Subsystems.Drive.tracking = true;
    // Sensors.Limelight.setCamMode(camMode.VISION_PROCESSOR);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double x = Sensors.Limelight.getHorizontalOffset() + offset;
    
    if (Math.abs(x) > 12) {
      left = (-x/50) - .12;
      right = (x/50) + .12;
    } else if (Math.abs(x) > 2) {
      if(x > 0) {
        left = -.12;
        right = .12;
      } else {
        left = .12;
        right = -.12;
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
    Robot.teleopPivot = false;
    Robot.limelightPivot = false;
    Subsystems.Drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
