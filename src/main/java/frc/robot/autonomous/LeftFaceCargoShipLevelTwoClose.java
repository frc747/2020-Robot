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

public class LeftFaceCargoShipLevelTwoClose extends SequentialCommandGroup {
  
  @Override
  public void initialize() {
    Robot.autoSideLeft = true;
    Robot.autoSideRight = false;
    Robot.autoSideFaceCargoShip = true;
    Robot.autoFrontFaceCargoShip = false;
    Robot.autoRocket = false;

    Robot.operatorControl = false;
    Robot.resetPigeonAngle();
  }

  // this autonomous routine runs assuming the robot starts at the furthest to the right and forward on the right side of level two
  public LeftFaceCargoShipLevelTwoClose() {
    
    // addSequential(new PauseCommand(4));
    //uncomment below BEFORE MIDKNIGHT
    addCommands(
      new PIDDriveInchesHoldHatch(109.775, false).withTimeout(4), 
      new PIDDriveRotateCustom(-21.5, false).withTimeout(4), 
      new PIDDriveInches(140.5, false).withTimeout(4), //was 143 when it overdrove in practice match
      new PIDDriveRotateCustom(90, false).withTimeout(4)
    );
    // driver takes over now
  }

  @Override
  public void end(boolean interrupted) {
    Robot.operatorControl = true;
  }
}