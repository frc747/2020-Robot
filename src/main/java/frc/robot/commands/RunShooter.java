/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Motors;
import frc.robot.Subsystems;
import frc.robot.input.Devices;

/**
 * This command revs the shooter flywheel
 */
public class RunShooter extends CommandBase {

  double calcRPM;

  public RunShooter() {
    addRequirements(Subsystems.Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Motors.shooter.configClosedloopRamp(1.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putString("execute: ", "EXECUTE!");
    if(Devices.xboxController.getLeftBumper() && Subsystems.Hood.hoodUp) {
      calcRPM = Subsystems.Shooter.getCalculatedRPM();
      Subsystems.Shooter.setRPM(calcRPM); // add automatic RPM adjustment
      if(Math.abs(Subsystems.Shooter.getRPM()-calcRPM) < 300) {
        Subsystems.Transfer.targetRPM = true;
      } else {
        Subsystems.Transfer.targetRPM = false;
      }
    } else {
      Subsystems.Transfer.targetRPM = false;
      Subsystems.Shooter.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Motors.shooter.configClosedloopRamp(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
