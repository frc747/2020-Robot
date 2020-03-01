/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Sensors;
import frc.robot.Subsystems;
import frc.robot.input.Devices;

public class SmartBallTransfer extends CommandBase {

  public SmartBallTransfer() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Subsystems.Transfer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // SmartDashboard.putBoolean("Select Button", Devices.operatorController.getSelect());
    if (Sensors.IRBreakBeam.getValue()) {
      Subsystems.Transfer.set(-0.3);
    } else {
      Subsystems.Transfer.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}