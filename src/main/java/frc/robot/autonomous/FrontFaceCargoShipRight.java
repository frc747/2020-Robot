package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
public class FrontFaceCargoShipRight extends SequentialCommandGroup {

  @Override
  public void initialize() {
    Robot.autoSideLeft = false;
    Robot.autoSideRight = true;
    Robot.autoSideFaceCargoShip = false;
    Robot.autoFrontFaceCargoShip = true;
    Robot.autoRocket = false;
    Robot.operatorControl = false;
    Robot.resetPigeonAngle();
  }

  public FrontFaceCargoShipRight() {
  }

  @Override
  public void end(boolean interrupted) {
    Robot.operatorControl = true;
  }
}
