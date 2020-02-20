/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import frc.robot.interfaces.IRBreakBeamInterface;
import frc.robot.interfaces.LIDARInterface;
import frc.robot.interfaces.LimelightInterface;
import frc.robot.interfaces.PigeonInterface;

/**
 * Add your docs here.
 */
public class Sensors {
  public static final IRBreakBeamInterface IRBreakBeam = new IRBreakBeamInterface(0);
  public static final LIDARInterface LIDAR = new LIDARInterface(I2C.Port.kOnboard);
  public static final LimelightInterface Limelight = new LimelightInterface();
  public static final PigeonInterface Pigeon = new PigeonInterface(0);
}
