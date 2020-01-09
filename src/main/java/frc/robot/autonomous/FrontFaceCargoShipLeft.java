package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class FrontFaceCargoShipLeft extends SequentialCommandGroup {

  @Override
  public void initialize() {
    Robot.autoSideLeft = true;
    Robot.autoSideRight = false;
    Robot.autoSideFaceCargoShip = false;
    Robot.autoFrontFaceCargoShip = true;
    Robot.autoRocket = false;
    Robot.operatorControl = false;
    Robot.resetPigeonAngle();
  }

  public FrontFaceCargoShipLeft() {
  }

  @Override
  public void end(boolean interrupted) {
    Robot.operatorControl = true;
  }
}
