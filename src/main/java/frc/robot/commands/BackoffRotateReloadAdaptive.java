package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class BackoffRotateReloadAdaptive extends SequentialCommandGroup {
  public BackoffRotateReloadAdaptive() {
    // if (!Robot.isTeleop) {

    //   if (Robot.autoSideFaceCargoShip) {
    //     if (Robot.autoSideLeft) {
    //       addCommands(
    //         new PIDDriveInches(46, true), 
    //         new PIDDriveRotateCustom(103, true)
    //       );
    //     } else if (Robot.autoSideRight) {
    //       addCommands(
    //         new PIDDriveInches(46, true), 
    //         new PIDDriveRotateCustom(-103, true)
    //       );
    //     }


    //   } else if (Robot.autoRocket) {
    //     if (Robot.autoSideLeft) {
    //       addCommands(
    //         new PIDDriveInches(15, true), 
    //         new PIDDriveRotateCustom(-150, true)
    //       );
    //     } else if (Robot.autoSideRight) {
    //         addCommands(
    //           new PIDDriveInches(15, true), 
    //           new PIDDriveRotateCustom(150, true)
    //         );
    //     }


    //   } else if (Robot.autoFrontFaceCargoShip) {
    //     if (Robot.autoSideLeft) {
    //       addCommands(
    //         new PIDDriveInches(10, true), 
    //         new PIDDriveRotateCustom(-143, true)
    //       );
    //     } else if (Robot.autoSideRight) {
    //       addCommands(
    //         new PIDDriveInches(10, true), 
    //         new PIDDriveRotateCustom(143, true)
    //       );
    //     }
    //   }
  

    // }

  }
}
