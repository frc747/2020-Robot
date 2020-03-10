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

  double max = .4;


  public LimelightServoSubsystem() {
    
  }

  public void set(double value) {
    if(value > max) {
      value = max;
    }
    Servos.limelightServo.set(value);;
  }

  public boolean checkServo() {
    return Servos.limelightServo.getPosition() == 0;
  }

  public void trackTarget() {
    double calc = ((Math.toDegrees(Math.atan(88/Sensors.LIDAR.getDistance())))/125)+((Sensors.Limelight.getVerticalOffset()/250));//*Sensors.LIDAR.getDistance()/45);
    SmartDashboard.putNumber("CALC: ", calc);

    if (calc > max) {
      calc = max;
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
