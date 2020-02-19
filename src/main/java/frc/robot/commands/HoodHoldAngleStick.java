/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HoodSubsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Motors;
import frc.robot.OI;


/* The point of this class is to mix the HoodStick and HoodToAngle classes. 
You move the hood with the left stick of the operator controller,
and when you want it to hold that angle, you hold the "Y" button. */


public class HoodHoldAngleStick extends CommandBase {
  
  HoodSubsystem subsystem;

  private double driveTicks;
  
  private double actualAngle;
  private double angleConstant = 17.5;

  private boolean check = false;

  private double holdGoal = 0;

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

  private double scalar = .1;

  public HoodHoldAngleStick(HoodSubsystem sub) {
    subsystem = sub;
    addRequirements(subsystem);


   // driveTicks = -(actualAngle/360)*4096;

    double distance = 89;

    driveTicks = calcTicks(distance);

    SmartDashboard.putNumber("ticks", calcTicks(distance));

    SmartDashboard.putNumber("DriveTicks", driveTicks);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    Motors.hood.setInverted(false);


    Motors.hood.set(ControlMode.MotionMagic, 0);
        
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
    if(OI.operatorController.getRawButton(4)) {
      if(!check) {
        check = true;
        holdGoal = subsystem.getPosition();
      }
      Motors.hood.set(ControlMode.MotionMagic, holdGoal);
      SmartDashboard.putBoolean("Inside motion magic: ", true);
    } else {
      check = false;
      SmartDashboard.putBoolean("Inside motion magic: ", false);
      subsystem.set(OI.operatorController.getRawAxis(1)*scalar);
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
