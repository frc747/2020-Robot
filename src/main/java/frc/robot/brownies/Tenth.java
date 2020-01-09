/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.brownies;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.PIDDriveInches;
import frc.robot.commands.PIDDriveRotate;

public class Tenth extends SequentialCommandGroup {
  /**
   * Add your docs here.
   */
  public Tenth() {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
    moveForward();
    moveBackward();
    rotateRight();
    reverseDirectionRight();
  }

  private void moveForward() {
    addCommands(new PIDDriveInches(24, false));
  }

  private void moveBackward() {
    addCommands(new PIDDriveInches(24, true));
  }

  private void rotateLeft() {
    addCommands(new PIDDriveRotate(-90));
  }

  private void rotateRight() {
    addCommands(new PIDDriveRotate(90));
  }

  private void reverseDirectionsLeft() {
    addCommands(new PIDDriveRotate(-180));
  }

  private void reverseDirectionRight() {
    addCommands(new PIDDriveRotate(180));
  }

  private void Turn360Left() {
    addCommands(
      new PIDDriveRotate(-180),
      new PIDDriveRotate(180)
    );
  }

  private void Turn360Right() {
    addCommands(
      new PIDDriveRotate(180),
      new PIDDriveRotate(-180)
    );
  }
}
