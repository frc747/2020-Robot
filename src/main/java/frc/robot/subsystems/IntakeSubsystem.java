/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import frc.robot.Motors;
import frc.robot.Subsystems;
import frc.robot.input.Devices;

public class IntakeSubsystem extends SubsystemBase {
 
  double calc;
  double kP = 50000; //this scales the intake roller speed from 0.50 to ~0.92

  public IntakeSubsystem() {
    Motors.leftIntakeArm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    Motors.rightIntakeArm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    Motors.leftIntakeArm.setInverted(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Arm Position: ", Motors.leftIntakeArm.getSelectedSensorPosition());
    SmartDashboard.putNumber("Right Arm Position: ", Motors.rightIntakeArm.getSelectedSensorPosition());
  }

  public void setIntake(double output) {
    Motors.intake.set(ControlMode.PercentOutput, output);
  }

 
  public void setIntakeArms() { //TODO: MAKE PID COMMAND 
    Motors.leftIntakeArm.set(ControlMode.PercentOutput, 0.1 * -Devices.xboxController.getRightY());
    Motors.rightIntakeArm.set(ControlMode.PercentOutput, 0.1 * -Devices.xboxController.getRightY());
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
    SmartDashboard.putNumber("Calced Value", calc);
    return calc;
  }


  public void resetLeftEncoder() {
    Motors.leftIntakeArm.setSelectedSensorPosition(0);
    try {
    Thread.sleep(100);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void resetRightEncoder() {
    Motors.rightIntakeArm.setSelectedSensorPosition(0);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void resetBothEncoders(){
    Motors.rightIntakeArm.setSelectedSensorPosition(0);
    Motors.leftIntakeArm.setSelectedSensorPosition(0);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public double getLeftPosition() {
    return Motors.leftIntakeArm.getSelectedSensorPosition();
  }

  public double getRightPosition() {
    return Motors.rightIntakeArm.getSelectedSensorPosition();
  }
}
