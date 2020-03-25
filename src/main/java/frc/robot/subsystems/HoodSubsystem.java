/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * This class controls all functionality related to the aileron 
 */
public class HoodSubsystem extends SubsystemBase {

  public boolean hoodUp = false;

  public int preset = 0;

  public HoodSubsystem() {
    Motors.hood.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    Motors.hood.setSensorPhase(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("ENCODER POS", getPosition());
  }

  public void set(double output) {
    Motors.hood.set(ControlMode.PercentOutput, output);
  }

  public void stop() {
    Motors.hood.set(ControlMode.PercentOutput, 0);
  }

  public int getPosition() {
    return Motors.hood.getSelectedSensorPosition();
  }

  public void resetPosition() {
    Motors.hood.setSelectedSensorPosition(0);
  }

  public boolean checkHood() {
    return Motors.hood.getSelectedSensorPosition() == 0;
  }
}
