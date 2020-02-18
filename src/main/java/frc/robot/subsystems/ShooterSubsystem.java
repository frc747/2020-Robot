/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Motors;
import frc.robot.Robot;
import frc.robot.commands.ShooterStick;
public class ShooterSubsystem extends SubsystemBase {
 
  //TalonFX shooterMotor = new TalonFX(13);

  double kP = 0.13;
  double kF = 0.04592;

  public ShooterSubsystem() {
    Motors.shooter.setInverted(false);
    Motors.shooter.setSensorPhase(false);
    Motors.shooter.config_kP(0, kP);
    Motors.shooter.config_kF(0, kF);
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

  public void stop() {
    Motors.shooter.set(ControlMode.PercentOutput, 0);
  }

  public double getRPM() {
    return (Motors.shooter.getSelectedSensorVelocity()*600)/2048;
  }
}
