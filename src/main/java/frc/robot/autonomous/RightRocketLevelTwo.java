package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.PIDDriveRotateCustom;
import frc.robot.commands.PIDDriveInchesHoldHatch;

public class RightRocketLevelTwo extends SequentialCommandGroup {

  @Override
  public void initialize() {
    Robot.autoSideLeft = false;
    Robot.autoSideRight = true;
    Robot.autoSideFaceCargoShip = false;
    Robot.autoFrontFaceCargoShip = false;
    Robot.autoRocket = true;

    Robot.operatorControl = false;
  }

   public RightRocketLevelTwo() {
    addCommands(
      new PIDDriveInchesHoldHatch(109.775, false).withTimeout(4),
      //new PIDDriveInchesHoldHatch(93, false).withTimeout(4),
      new PIDDriveRotateCustom(45, false).withTimeout(4)
    );
  }

  @Override
  public void end(boolean interrupted) {
    Robot.operatorControl = true;
  }
}
