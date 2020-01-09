/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;

public class RightFaceCargoShipLevelTwoFar extends SequentialCommandGroup {

  @Override
  public void initialize() {
    Robot.autoSideLeft = false;
    Robot.autoSideRight = true;
    Robot.autoSideFaceCargoShip = true;
    Robot.autoFrontFaceCargoShip = false;
    Robot.autoRocket = false;

    Robot.operatorControl = false;
  }

  // this autonomous routine runs assuming the robot starts at the furthest to the right and forward on the right side of level two
  public RightFaceCargoShipLevelTwoFar() {
    // addSequential(new PauseCommand(4));
    //uncomment before MIDKNIGHT
    addCommands(
      new PIDDriveInchesHoldHatch(109.775, false).withTimeout(4),
      new PIDDriveRotateCustom(21.5, false).withTimeout(4),
      new PIDDriveInches(140.5, false).withTimeout(4),
      new PIDDriveRotateCustom(0, false).withTimeout(4),
      new PIDDriveInches(43.5, false).withTimeout(4), //was 143 when it overdrove in practice match
      new PIDDriveRotateCustom(-90, false).withTimeout(4)
    );
    // driver takes over now
  }

  @Override
  public void end(boolean interrupted) {
    Robot.operatorControl = true;
  }
}