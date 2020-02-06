/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.HoodSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
public class HoodToAngle extends CommandBase {
  
  HoodSubsystem subsystem;

  private double driveTicks;
  
  private double actualAngle;
  private double angleConstant = 17.5;

  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;
  private static final int slotIdx = 0;

  private static final double MAX_PERCENT_VOLTAGE = 1.0;
  private static final double MIN_PERCENT_VOLTAGE = 0.0;

  private final static int allowableCloseLoopError = 1;
  
  private double driveHatchP = 1;
  
  private double driveHatchI = 0;
  
  private double driveHatchD = 0;
  
  private double driveHatchF = 1;

  public HoodToAngle(double angle, HoodSubsystem sub) {
    subsystem = sub;
    addRequirements(subsystem);

    actualAngle = angle;

   // driveTicks = -(actualAngle/360)*4096;

    double distance = 77;

    driveTicks = calcTicks(distance);

    SmartDashboard.putNumber("ticks", calcTicks(distance));

    SmartDashboard.putNumber("DriveTicks", driveTicks);

    subsystem.resetPosition();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    Robot.HOOD_SUBSYSTEM.hoodMotor.setInverted(false);


    Robot.HOOD_SUBSYSTEM.hoodMotor.set(ControlMode.MotionMagic, 0);
        
    Robot.HOOD_SUBSYSTEM.hoodMotor.config_kP(pidIdx, driveHatchP, timeoutMs);
        
    Robot.HOOD_SUBSYSTEM.hoodMotor.config_kI(pidIdx, driveHatchI, timeoutMs);
        
    Robot.HOOD_SUBSYSTEM.hoodMotor.config_kD(pidIdx, driveHatchD, timeoutMs);
        
    Robot.HOOD_SUBSYSTEM.hoodMotor.config_kF(pidIdx, driveHatchF, timeoutMs);
        
    Robot.HOOD_SUBSYSTEM.hoodMotor.setSensorPhase(true);

    Robot.HOOD_SUBSYSTEM.hoodMotor.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.HOOD_SUBSYSTEM.hoodMotor.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
    Robot.HOOD_SUBSYSTEM.hoodMotor.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
    Robot.HOOD_SUBSYSTEM.hoodMotor.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
    Robot.HOOD_SUBSYSTEM.hoodMotor.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        
    Robot.HOOD_SUBSYSTEM.hoodMotor.configMotionCruiseVelocity(7500, 10); //7500
    Robot.HOOD_SUBSYSTEM.hoodMotor.configMotionAcceleration(20000, 10);

    Robot.HOOD_SUBSYSTEM.hoodMotor.set(ControlMode.MotionMagic, 0);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!Robot.m_oi.operatorController.getRawButton(7)) {
      Robot.HOOD_SUBSYSTEM.hoodMotor.set(ControlMode.MotionMagic, 0);
    } else {
      Robot.HOOD_SUBSYSTEM.hoodMotor.set(ControlMode.MotionMagic, driveTicks);
    }
  }


  public double calcTicks(double distance) {
    double ticks;
    if(distance < 25) {
      ticks = -1200;
    } else {
      double aCoeff = -.02963599;
      double bCoeff = 9.447184;
      double cCoeff = -1026.159;
      ticks = (aCoeff*distance*distance)+(bCoeff*distance)+(cCoeff);
    }


    return ticks;
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
