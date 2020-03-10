/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Motors;
import frc.robot.Robot;
import frc.robot.Sensors;
import frc.robot.Subsystems;
import frc.robot.input.Devices;
public class HoodToAngle extends CommandBase {

  private double zeroDistance = 182;
  private double linearCoefficent;

  private double preset_1_ticks = ticksFromAngle(20);
  private double preset_2_ticks = ticksFromAngle(30);
  private double preset_3_ticks = ticksFromAngle(40);
  private double preset_4_ticks = ticksFromAngle(50);
  private double preset_5_ticks = ticksFromAngle(60);



  private double driveTicks;
  
  private double actualAngle;
  private double angleConstant = 17.5;

  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;
  private static final int slotIdx = 0;

  private static final double MAX_PERCENT_VOLTAGE = .30
  ;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  private final static int allowableCloseLoopError = 1;
  
  private double driveHatchP = 2;
  
  private double driveHatchI = 0;
  
  private double driveHatchD = .1;
  
  private double driveHatchF = .5;

  /***
   * 
   * @param angle
   * Use this to move the hood to a specific angle positive up from 0 degrees (horizontal)
   * Do not provide one to auto adjust based on LIDAR Distance
   */

  public HoodToAngle(double angle) {
    addRequirements(Subsystems.Hood);

    actualAngle = angle + angleConstant;

    driveTicks = -(actualAngle/360)*4096;

    SmartDashboard.putNumber("DriveTicks test", driveTicks);
  }

  public HoodToAngle() {
    addRequirements(Subsystems.Hood);

    actualAngle = angleFromDistance(Sensors.LIDAR.getDistance()) + angleConstant;

    driveTicks = -(actualAngle/360)*4096;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    Motors.hood.setInverted(false);

    Motors.hood.set(ControlMode.MotionMagic, -200);
        
    Motors.hood.config_kP(pidIdx, driveHatchP, timeoutMs);
        
    Motors.hood.config_kI(pidIdx, driveHatchI, timeoutMs);
        
    Motors.hood.config_kD(pidIdx, driveHatchD, timeoutMs);
        
    Motors.hood.config_kF(pidIdx, driveHatchF, timeoutMs);
        
    Motors.hood.setSensorPhase(true);

    Motors.hood.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Motors.hood.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Motors.hood.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Motors.hood.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
    Motors.hood.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
    Motors.hood.configMotionCruiseVelocity(7500, 10); //7500
    Motors.hood.configMotionAcceleration(20000, 10);


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    actualAngle = angleFromDistance(Sensors.LIDAR.getDistance()) + angleConstant;
    driveTicks = -(actualAngle/360)*4096;
    if(driveTicks > -200) {
      driveTicks = -200;
    }
    SmartDashboard.putNumber("angle: ", actualAngle);
    SmartDashboard.putNumber("DriveTicks for hood: ", driveTicks);
    switch (Subsystems.Hood.preset) {
      case 0:
        if(!Robot.teleopPivot) {
          Motors.hood.set(ControlMode.MotionMagic, -200);
          Subsystems.Hood.hoodUp = false;
        } else if (IntakeCommand.armState % 2 == 0) {
          Motors.hood.set(ControlMode.MotionMagic, driveTicks);
          Subsystems.Hood.hoodUp = true;
        }
        break;
      case 1:
        Motors.hood.set(ControlMode.MotionMagic, preset_1_ticks);
        Subsystems.Hood.hoodUp = true;
        break;
      case 2:
        Motors.hood.set(ControlMode.MotionMagic, preset_2_ticks);
        Subsystems.Hood.hoodUp = true;
        break;
      case 3:
        Motors.hood.set(ControlMode.MotionMagic, preset_3_ticks);
        Subsystems.Hood.hoodUp = true;
        break;  
      case 4:
        Motors.hood.set(ControlMode.MotionMagic, preset_4_ticks);
        Subsystems.Hood.hoodUp = true;
        break;
      case 5:
        Motors.hood.set(ControlMode.MotionMagic, preset_5_ticks);
        Subsystems.Hood.hoodUp = true;
        break;
        

    }
    
  }

  public double angleFromDistance(double distance) {
    if(distance > 35) {
      Robot.under35 = false;
       linearCoefficent = -(Sensors.LIDAR.getDistance()-zeroDistance)/13;
      return Math.toDegrees( Math.atan( ( 1.45 * (98.25 - 20.375/* robot height */) ) / distance ) )/1.1 + linearCoefficent;
    } else if(distance > 175) {
      return 30;
    } else {
      Robot.under35 = true;
      return 100;
    }
  }

  public double ticksFromAngle(double angle) {
    double actualAngle = angle + angleConstant;
    return -(angleConstant/360)*4096;
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
