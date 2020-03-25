/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Subsystems;

/**
 * This command controls the indexer bands during autonomous
 */
public class RunIndexerAuto extends CommandBase {

  public RunIndexerAuto() {
    addRequirements(Subsystems.Indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      if ((int)Timer.getFPGATimestamp() % 2 == 0) {
        Subsystems.Indexer.set(-0.75); // change to setInverted once motor direction has been confirmed
      } else {
        Subsystems.Indexer.set(0.75);
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Subsystems.Indexer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
