/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Subsystems;

/**
 * This command sets the Limelight servo to track the target
 */
public class ServoAngle extends CommandBase {

  public ServoAngle() {
    addRequirements(Subsystems.LimelightServo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Robot.limelightPivot) {
      Subsystems.LimelightServo.trackTarget();
    } else {
      Subsystems.LimelightServo.set(0);
    }
    //Subsystems.LimelightServo.set(Devices.operatorController.getLeftY());
    //Subsystems.LimelightServo.trackTarget();
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
