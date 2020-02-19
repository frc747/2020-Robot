/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.interfaces;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IRBreakBeamInterface extends SubsystemBase {

  public static DigitalInput IRBreakBeamRaw;

  public IRBreakBeamInterface(int port) {
    IRBreakBeamRaw = new DigitalInput(port);
  }

  public boolean getValue() {
    return IRBreakBeamRaw.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
