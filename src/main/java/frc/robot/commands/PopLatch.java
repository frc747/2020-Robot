/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Solenoids;
import frc.robot.Subsystems;

public class PopLatch extends CommandBase {
  /**
   * Creates a new PopLatch.
   */
  public PopLatch() {
    addRequirements(Subsystems.Climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Solenoids.climbLatch.set(true);
    Subsystems.Climb.set(.25);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Subsystems.Climb.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
