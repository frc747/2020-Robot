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
 * This command runs the transfer wheel during auto
 */
public class RunTransferAuto extends CommandBase {

  public RunTransferAuto() {
    addRequirements(Subsystems.Transfer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Subsystems.Transfer.targetRPM) {
       Subsystems.Transfer.set(-.8);
    } else if(Sensors.IRBreakBeam.getValue()){
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
