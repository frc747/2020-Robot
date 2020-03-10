/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.IntakeCommandAuto;
import frc.robot.commands.PIDDriveInchesSkew;
import frc.robot.commands.RunIndexerAuto;
import frc.robot.commands.RunTransferPrepAuto;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RetreatTrenchRun extends ParallelCommandGroup {
  /**
   * Creates a new RetreatTrenchRun.
   */
  public RetreatTrenchRun() {
    // Add your commands in the super() call, e.g.
    super(new PIDDriveInchesSkew(250, true), new IntakeCommandAuto(), new RunIndexerAuto(), new RunTransferPrepAuto());
  }
}
