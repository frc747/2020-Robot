/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Sensors;
import frc.robot.Servos;
import java.lang.Math;
public class LimelightServoSubsystem extends SubsystemBase {

  public LimelightServoSubsystem() {
    
  }

  public void set(double value) {
    if(value > .3) {
      value = .3;
    }
    Servos.limelightServo.set(value);;
  }

  public void trackTarget() {
    //currentPos = Servos.limelightServo.getAngle();
    //Servos.limelightServo.setAngle((kP*Sensors.Limelight.getVerticalOffset()));




    double calc = ((Math.toDegrees(Math.atan(88/Sensors.LIDAR.getDistance())))/240)+((Sensors.Limelight.getVerticalOffset()/600));//*Sensors.LIDAR.getDistance()/45);
    SmartDashboard.putNumber("CALC: ", calc);

    if (calc > .3) {
      calc = .3;
    }

    Servos.limelightServo.set(calc);
  }

  public void printOuts() {
    SmartDashboard.putNumber("LIMELIGHT VERITCAL OFFSET", Sensors.Limelight.getVerticalOffset());
    SmartDashboard.putNumber("Servo Value: ", Servos.limelightServo.get());
  }

  @Override
  public void periodic() {
  }
}
