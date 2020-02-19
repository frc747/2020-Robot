/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.interfaces;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PigeonInterface extends SubsystemBase {

  public static PigeonIMU PigeonRaw;

  private static double[] ypr = new double[3];

  public PigeonInterface(int id) {
    PigeonRaw = new PigeonIMU(id);
  }
  
  public double getAngle() {
    PigeonRaw.getYawPitchRoll(ypr);

    if (-ypr[0] > 0) {
      return ((-ypr[0]+180)%360)-180; //limits angle to range of -180 to 180
    } else {
      return ((-ypr[0]-180)%360)+180; //limits angle to range of -180 to 180
    }
  }
  
  public double getRawAngle() {
    PigeonRaw.getYawPitchRoll(ypr); 
    return -ypr[0];     //returns exact pigeon angle without range limiting
  }

  public double getAngleRadians() {
    return Math.toRadians(this.getAngle());
  }
  
  public double getRawAngleRadians() {
    return Math.toRadians(this.getRawAngle());
  }

  public void resetAngle() {
    PigeonRaw.setYaw(0);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
