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
  double offset;
  public RotateVision(double x_offset) {
    addRequirements(Subsystems.Drive);
    offset = x_offset;
  }

  public RotateVision() {
    addRequirements(Subsystems.Drive);
    offset = 0;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.limelightPivot = true;
    Robot.teleopPivot = true;
    // Sensors.Limelight.setPipeline(0);
    Subsystems.Drive.tracking = true;
    // Sensors.Limelight.setCamMode(camMode.VISION_PROCESSOR);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double x = Sensors.Limelight.getHorizontalOffset() + offset;

    if(!(Sensors.LIDAR.getDistance() < 10 && Subsystems.Hood.preset == 4) && !(Subsystems.Hood.preset == 5)) {
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
    }
    
    
    

    switch(Subsystems.Hood.preset) {
      case 0:
        if (Sensors.LIDAR.getDistance() <= 40 && Math.abs(x) < 2) {
          left = -.2;
          right = -.2;
        }
        break;
      case 1:
        
        if (Sensors.LIDAR.getDistance() <= 40 && Math.abs(x) < 2) {
          left = -.2;
          right = -.2;
        } else if (Sensors.LIDAR.getDistance() >= 44 && Math.abs(x) < 2) {
          left = .2;
          right = .2;
        }
        break;
      case 2:
        if (Sensors.LIDAR.getDistance() <= 208 && Math.abs(x) < 2) {
          left = -.2;
          right = -.2;
        } else if (Sensors.LIDAR.getDistance() >= 212 && Math.abs(x) < 2) {
          left = .2;
          right = .2;
        }
        break;
      case 3:
        /*if (Sensors.LIDAR.getDistance() <= 290 && Math.abs(x) < 2) {
          left = -.2;
          right = -.2;
        } else if (Sensors.LIDAR.getDistance() >= 294 && Math.abs(x) < 2) {
          left = .2;
          right = .2;
        }*/
        left = 0;
        right = 0;
        break;
      case 4:
      if (Sensors.LIDAR.getDistance() >= 15 && Math.abs(x) < 2) {
        left = .25;
        right = .25;
      } else if (Sensors.LIDAR.getDistance() < 15 && Sensors.LIDAR.getDistance() > 8) {
        left = .25;
        right = .25;
      } else if(Sensors.LIDAR.getDistance() < 8){
        left = 0;
        right = 0;
      }
        break;
      case 5:
       
        break;
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
    Robot.teleopPivot = false;
    Subsystems.Drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
