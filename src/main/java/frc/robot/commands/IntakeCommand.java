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

public class IntakeCommand extends CommandBase {

  public IntakeCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Subsystems.Intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Subsystems.Intake.setIntakeArms();

    //remove
    if (Devices.operatorController.getLeftTrigger() >= 0.9) {
      Subsystems.Intake.setIntake(0.75); 
    }/* else if (Devices.operatorController.getRightTrigger() >= 0.9) {
      Subsystems.Intake.setIntake(-0.75); 
    } */else {
      Subsystems.Intake.stopIntake();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Subsystems.Intake.stopIntakeArms();
    //remove
    Subsystems.Intake.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
