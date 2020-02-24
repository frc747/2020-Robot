/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import frc.robot.Motors;
import frc.robot.Subsystems;
import frc.robot.input.Devices;

public class IntakeSubsystem extends SubsystemBase {
 
  double calc;
  double kP = 1000;

  public IntakeSubsystem() {
    Motors.leftIntakeArm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    Motors.rightIntakeArm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    Motors.leftIntakeArm.setInverted(true);
    Motors.leftIntakeArm.setSelectedSensorPosition(0);
    Motors.rightIntakeArm.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setIntake(double output) {
    Motors.intake.set(ControlMode.PercentOutput, output);
  }

 
  public void setIntakeArms() { //TODO: MAKE PID COMMAND 
    Motors.leftIntakeArm.set(ControlMode.PercentOutput, 0.1 * -Devices.operatorController.getRightY());
    Motors.rightIntakeArm.set(ControlMode.PercentOutput, 0.1 * -Devices.operatorController.getRightY());
  }

  public void stopIntake() {
    Motors.intake.set(ControlMode.PercentOutput, 0);
  }

  public void stopIntakeArms() {
    Motors.leftIntakeArm.set(ControlMode.PercentOutput, 0);
    Motors.rightIntakeArm.set(ControlMode.PercentOutput, 0);
  }

  public double proportionalIntake() {
    calc = 0.5+(Subsystems.Drive.getAverageVelocity()/kP);
    return calc;
  }
}
