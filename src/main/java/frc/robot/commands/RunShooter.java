/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Subsystems;
import frc.robot.input.Devices;

public class RunShooter extends CommandBase {

  public RunShooter() {
    addRequirements(Subsystems.Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putString("execute: ", "EXECUTE!");
    if(Devices.operatorController.getRightTrigger() > 0.9) {
      Subsystems.Shooter.setRPM(6000); // add automatic RPM adjustment based on LIDAR
    } else {
      Subsystems.Shooter.stop();
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