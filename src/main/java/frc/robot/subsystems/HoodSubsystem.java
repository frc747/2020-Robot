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
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class HoodSubsystem extends SubsystemBase {
  
  public TalonSRX hoodMotor = new TalonSRX(10);

  public HoodSubsystem() {
    hoodMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    hoodMotor.setSensorPhase(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("ENCODER POS", getPosition());
  }

  public void set(double output) {
    hoodMotor.set(ControlMode.PercentOutput, output);
  }

  public void stop() {
    hoodMotor.set(ControlMode.PercentOutput, 0);
  }

  public int getPosition() {
    return hoodMotor.getSelectedSensorPosition();
  }

  public void resetPosition() {
    hoodMotor.setSelectedSensorPosition(0);
  }
}
