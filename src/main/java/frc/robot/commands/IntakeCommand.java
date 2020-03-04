/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Motors;
import frc.robot.Subsystems;
import frc.robot.input.Devices;

public class IntakeCommand extends CommandBase {  

  public static int armState = 0;

  public IntakeCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Subsystems.Intake);
  }
  private double tickGoal;
  
  private final static double STOP_THRESHOLD_TICKS = 2500;

  private final static int TARGET_COUNT_ONE_SECOND = 50;
  private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .1;

  private double driveP = 0.01;
  private double driveI = 0.0;
  private double driveD = 0.000;
  private double driveF = 0.0;

  private double floorPosition = 0;
  private double uprightPosition = 45000;

  private boolean runMotionMagic = false;
  private boolean armsAreUp = false;
  private boolean armsAreDown = false;
  private boolean drivingUp = false;
  private boolean drivingDown = false;


  private static final double ARM_MAX_VOLTAGE = 2.0;//6.0;
  private static final double ARM_MIN_VOLTAGE = 0.0;

  private static final double ARM_MAX_PERCENT_VOLTAGE = ARM_MAX_VOLTAGE / 12;
  private static final double ARM_MIN_PERCENT_VOLTAGE = ARM_MIN_VOLTAGE / 12;


  private int onTargetCount = 0;

  private final static int allowableCloseLoopError = 1;

  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;
  private static final int slotIdx = 0;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    onTargetCount = 0;
    runMotionMagic = false;


    Motors.leftIntakeArm.setInverted(true);
    Motors.leftIntakeArm.setSensorPhase(false);

    // Motors.rightIntakeArm.setInverted(false);
    // Motors.rightIntakeArm.setSensorPhase(false);

    Motors.leftIntakeArm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    Motors.rightIntakeArm.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    Motors.leftIntakeArm.setSelectedSensorPosition(0);
    Motors.rightIntakeArm.setSelectedSensorPosition(0);

    Motors.leftIntakeArm.config_kP(pidIdx, driveP, timeoutMs);
    Motors.rightIntakeArm.config_kP(pidIdx, driveP, timeoutMs);
    
    Motors.leftIntakeArm.config_kI(pidIdx, driveI, timeoutMs);
    Motors.rightIntakeArm.config_kI(pidIdx, driveI, timeoutMs);
    
    Motors.leftIntakeArm.config_kD(pidIdx, driveD, timeoutMs);
    Motors.rightIntakeArm.config_kD(pidIdx, driveD, timeoutMs);
    
    Motors.leftIntakeArm.config_kF(pidIdx, driveF, timeoutMs);
    Motors.rightIntakeArm.config_kF(pidIdx, driveF, timeoutMs);

    Motors.leftIntakeArm.configNominalOutputForward(+ARM_MIN_PERCENT_VOLTAGE, 0);
    Motors.leftIntakeArm.configNominalOutputReverse(-ARM_MIN_PERCENT_VOLTAGE, 0);
    Motors.leftIntakeArm.configPeakOutputForward(+ARM_MAX_PERCENT_VOLTAGE, 0);
    Motors.leftIntakeArm.configPeakOutputReverse(-ARM_MAX_PERCENT_VOLTAGE, 0);

    Motors.rightIntakeArm.configNominalOutputForward(+ARM_MIN_PERCENT_VOLTAGE, 0);
    Motors.rightIntakeArm.configNominalOutputReverse(-ARM_MIN_PERCENT_VOLTAGE, 0);
    Motors.rightIntakeArm.configPeakOutputForward(+ARM_MAX_PERCENT_VOLTAGE, 0);
    Motors.rightIntakeArm.configPeakOutputReverse(-ARM_MAX_PERCENT_VOLTAGE, 0);

    Motors.leftIntakeArm.configClosedLoopPeakOutput(0, ARM_MAX_PERCENT_VOLTAGE);
    Motors.rightIntakeArm.configClosedLoopPeakOutput(0, ARM_MAX_PERCENT_VOLTAGE);

    Motors.leftIntakeArm.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
    Motors.rightIntakeArm.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);

    Motors.leftDrivePrimary.configMotionCruiseVelocity(1000, timeoutMs);
    Motors.leftDrivePrimary.configMotionAcceleration(500, timeoutMs);
    Motors.rightDrivePrimary.configMotionCruiseVelocity(1000, timeoutMs);
    Motors.rightDrivePrimary.configMotionAcceleration(500, timeoutMs);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPosition = Subsystems.Intake.getLeftPosition();
    double rightPosition = Subsystems.Intake.getRightPosition();
    // Subsystems.Intake.setIntakeArms();

    //remove
    if (Devices.xboxController.getLeftTrigger() >= 0.9) {
      Subsystems.Intake.setIntake(0.70/*Subsystems.Intake.proportionalIntake()*/);
    } else if (Devices.xboxController.getX() && armState % 2 == 0) {
      Subsystems.Intake.setIntake(-0.70/*-Subsystems.Intake.proportionalIntake()*/);
    } else {
      Subsystems.Intake.stopIntake();
    }

    if (armState % 2 == 1) {
      tickGoal = uprightPosition;
      runMotionMagic = true;
      drivingDown = false;
      drivingUp = true;
    } else if (armState % 2 == 0) {
      tickGoal = floorPosition;
      runMotionMagic = true;
      drivingDown = true;
      drivingUp = false;
    } else {
      tickGoal = Subsystems.Intake.getLeftPosition();
      drivingDown = false;
      drivingUp = false;
      runMotionMagic = false;
    }

    if ((leftPosition > (tickGoal - STOP_THRESHOLD_TICKS) && leftPosition < (tickGoal + STOP_THRESHOLD_TICKS)) ||
      (rightPosition > (tickGoal - STOP_THRESHOLD_TICKS) && rightPosition < (tickGoal + STOP_THRESHOLD_TICKS))) {
      onTargetCount++;
    } else {
      onTargetCount = 0;
    }

    if (onTargetCount > ON_TARGET_MINIMUM_COUNT) {
      drivingDown = false;
      drivingUp = false;
      runMotionMagic = true;
    }

    if (runMotionMagic) {
      Motors.rightIntakeArm.set(ControlMode.MotionMagic, tickGoal);
      Motors.leftIntakeArm.set(ControlMode.MotionMagic, tickGoal);
    } else {
      Motors.rightIntakeArm.set(ControlMode.PercentOutput, 0.0);
      Motors.leftIntakeArm.set(ControlMode.PercentOutput, 0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Subsystems.Intake.stopIntakeArms();
    //remove
    Subsystems.Intake.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // double leftPosition = Subsystems.Intake.getLeftPosition();
    // double rightPosition = Subsystems.Intake.getRightPosition();

    // SmartDashboard.putNumber("Intake Arm TICK GOAL: ", tickGoal);

    // if ((leftPosition > (tickGoal - STOP_THRESHOLD_TICKS) && leftPosition < (tickGoal + STOP_THRESHOLD_TICKS)) ||
    //     (rightPosition > (tickGoal - STOP_THRESHOLD_TICKS) && rightPosition < (tickGoal + STOP_THRESHOLD_TICKS))) {
    //   onTargetCount++;
    // } else {
    //   onTargetCount = 0;

    // }
    // SmartDashboard.putNumber("ONTARGETCOUNT ARMS", onTargetCount);
    // return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
    return false;
  }
}
