/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Motors;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.Subsystems;
import frc.robot.interfaces.LIDARInterface;

/**
 * This subsystem controls all functionality related to the shooter
 */
public class ShooterSubsystem extends SubsystemBase {

  private double preset_1_rpm = 6000; // CLOSE SHOTS
  private double preset_2_rpm = 6000; // TRENCH RUN
  private double preset_3_rpm = 6000; // DEEP SHOTS
  private double preset_4_rpm = 2500; // LOW GOAL
  private double preset_5_rpm = 2500; // PASSER

  double zeroDistance = 182;
  public double linearCoefficent;
  double kP = 0.13;
  double kF = 0.04592;

  double shooterHeight = 21;
  double targetHeight= 98.25 - shooterHeight;

  double startingLinearVelocity;
  double targetRPM;
  public ShooterSubsystem() {
    Motors.shooter.setInverted(false);
    Motors.shooter.setSensorPhase(false);
    Motors.shooter.config_kP(0, kP);
    Motors.shooter.config_kF(0, kF);
    Motors.shooter.setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("RPM", getRPM());
  }

  public void set(double output) {
    Motors.shooter.set(ControlMode.PercentOutput, output);
  }

  public void setRPM(double rpm) {
    // double output = rpm/6380;
    // shooterMotor.set(ControlMode.Velocity, output);
    double output = /*(rpm*2048)/600;.//correct*/(rpm*2048)/600;
    Motors.shooter.set(ControlMode.Velocity, output);
  }

  public double getCalculatedRPM() {
    double horDistanceToTarget = Sensors.LIDAR.getDistance();
    linearCoefficent = -(Sensors.LIDAR.getDistance()-zeroDistance)*80;

    switch(Subsystems.Hood.preset) {
      case 0:
        if(horDistanceToTarget < 175) {
          startingLinearVelocity = 1.33*Math.sqrt((9.81*(Math.pow(horDistanceToTarget, 2) + (4 * Math.pow(targetHeight, 2)))/2*targetHeight)) + linearCoefficent;
        } else {
          //startingLinearVelocity = 6000-(Math.sqrt(horDistanceToTarget-175)*205);
          startingLinearVelocity = 6000;
        }
        break;
      case 1:
        startingLinearVelocity = preset_1_rpm;
        break;
      case 2:
        startingLinearVelocity = preset_2_rpm;
        break;
      case 3:
        startingLinearVelocity = preset_3_rpm;
        break;
      case 4:
        startingLinearVelocity = preset_4_rpm;
        break;
      case 5:
        startingLinearVelocity = preset_5_rpm;
        break;
        
    }
    


    if(startingLinearVelocity > 6000 || Robot.under35) {
      startingLinearVelocity = 6000;
    }
    SmartDashboard.putNumber("Calculated RPM: ", startingLinearVelocity);
    return startingLinearVelocity;
  } 

  public void stop() {
    Motors.shooter.set(ControlMode.PercentOutput, 0);
  }

  public double getRPM() {
    return (Motors.shooter.getSelectedSensorVelocity()*600)/2048;
  }
}
