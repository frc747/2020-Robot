/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Sensors;
import frc.robot.Subsystems;

/**
 * This command preps loads the shooter, but does
 * not fire, during auto
 */
public class RunTransferPrepAuto extends CommandBase {

  public RunTransferPrepAuto() {
    addRequirements(Subsystems.Transfer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if( Sensors.IRBreakBeam.getValue()){
       Subsystems.Transfer.set(-.4);
    } else {
      Subsystems.Transfer.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Subsystems.Transfer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
