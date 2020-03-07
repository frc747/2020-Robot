/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.HoodToAngleAuto;
import frc.robot.commands.IntakeCommandAuto;
import frc.robot.commands.RunShooterAuto;
import frc.robot.commands.RunTransferAuto;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TrenchAuto extends ParallelCommandGroup {
  /**
   * Creates a new TrenchAuto.
   */
  public TrenchAuto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new HoodToAngleAuto(47).withTimeout(6), new RunTransferAuto().withTimeout(6), new RunShooterAuto().withTimeout(6));
  }
}
