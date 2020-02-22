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

  private double currentPos;//0.5; // set initial angle here
  private double kP = 2;
  public LimelightServoSubsystem() {
    currentPos = Servos.limelightServo.getAngle();
  }

  public void set(double value) {
    Servos.limelightServo.set(value);;
  }

  public void trackTarget() {
    //currentPos = Servos.limelightServo.getAngle();
    //Servos.limelightServo.setAngle((kP*Sensors.Limelight.getVerticalOffset()));
    double calc = ((Math.toDegrees(Math.atan(88/Sensors.LIDAR.getDistance())))/240)+((Sensors.Limelight.getVerticalOffset()/600));//*Sensors.LIDAR.getDistance()/45);
    SmartDashboard.putNumber("CALC: ", calc);
    Servos.limelightServo.set(calc);
  }

  public void printOuts() {
    currentPos = Servos.limelightServo.getAngle();
    SmartDashboard.putNumber("ANGLE TO TURN", (kP*Sensors.Limelight.getVerticalOffset()));
    SmartDashboard.putNumber("Current Pos", currentPos);
    SmartDashboard.putNumber("LIMELIGHT VERITCAL OFFSET", Sensors.Limelight.getVerticalOffset());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
