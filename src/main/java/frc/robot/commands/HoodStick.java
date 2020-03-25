/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Subsystems;
import frc.robot.input.Devices;

/**
 * This command controls the Aileron via thumbstick
 */
public class HoodStick extends CommandBase {

  double max = .1;

  public HoodStick() {
    addRequirements(Subsystems.Hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double leftStick = Devices.xboxController.getLeftY();

    if(leftStick > max) {
      leftStick = max;
    } else if (leftStick < -max) {
      leftStick = -max;
    }

    Subsystems.Hood.set(leftStick);
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
