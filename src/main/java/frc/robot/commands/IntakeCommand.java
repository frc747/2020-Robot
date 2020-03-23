/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Motors;
import frc.robot.Subsystems;
import frc.robot.input.Devices;

public class IntakeCommand extends CommandBase {  

  // if runMotionMagic == true
  // when arm state = 0, the robot will try to drive the arms towards the ground position
  // when arm state = 1, the robot will try to drive the arms towards the upright poistion
  public static int armState = 0;

  public IntakeCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Subsystems.Intake);
  }
  private double tickGoal;
  
  private final static double ON_TARGET_MINIMUM_SECONDS = 0.2;
  private final static double STOP_THRESHOLD_TICKS = 2500;


  private final static int TARGET_COUNT_ONE_SECOND = 50;
  private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * ON_TARGET_MINIMUM_SECONDS;

  // will have to make floorPosition = -45000 and uprightPosition 0
  private double uprightPosition = 45000;
  private double floorPosition = 0;


  private double driveP;
  private double driveI;
  private double driveD;
  private double driveF;

  private double driveUpP = 0.01; //0.01;
  private double driveUpI = 0.0;
  private double driveUpD = 0.000;
  private double driveUpF = 0.00;

  private double driveDownP = 0.01;
  private double driveDownI = 0.0;
  private double driveDownD = 0.000;
  private double driveDownF = 0.0;

  // TODO: will have to swap armsAreUp and armsAreDown starting configuration
  private boolean runMotionMagic = false;
  private boolean armsAreUp = false;
  private boolean armsAreDown = true;

  private boolean drivingUp = false;
  private boolean drivingDown = false;


  private static final double ARM_MAX_VOLTAGE = 6.0;//6.0;
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

    Motors.leftIntakeArm.configMotionCruiseVelocity(0, timeoutMs); // was testing with 10000
    Motors.leftIntakeArm.configMotionAcceleration(0, timeoutMs); // " " 500
    Motors.rightIntakeArm.configMotionCruiseVelocity(0, timeoutMs); // " " 10000
    Motors.rightIntakeArm.configMotionAcceleration(0, timeoutMs); // " " 500
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPosition = Subsystems.Intake.getLeftPosition();
    double rightPosition = Subsystems.Intake.getRightPosition();
    // Subsystems.Intake.setIntakeArms();

    //remove
    if (Devices.xboxController.getLeftTrigger() >= 0.9 && armState % 2 == 0 || (Subsystems.Transfer.targetRPM && Subsystems.Hood.hoodUp && Devices.xboxController.getRightBumper())) {
      Subsystems.Intake.setIntake(.7/*Subsystems.Intake.proportionalIntake()*/);
    } else if (Devices.xboxController.getX() && armState % 2 == 0) {
      Subsystems.Intake.setIntake(-.7/*-Subsystems.Intake.proportionalIntake()*/);
    } else {
      Subsystems.Intake.stopIntake();
    }


    if (armState % 2 == 1) {
      tickGoal = uprightPosition;
      // drivingDown = false;
      // drivingUp = true;
      driveP = driveUpP;
      driveI = driveUpI;
      driveD = driveUpD;
      driveF = driveUpF;
    } else if (armState % 2 == 0) {
      tickGoal = floorPosition;
      // runMotionMagic = true;
      // drivingDown = true;
      // drivingUp = false;
      driveP = driveDownP;
      driveI = driveDownI;
      driveD = driveDownD;
      driveF = driveDownF;
    }


    if ((leftPosition > (tickGoal - STOP_THRESHOLD_TICKS) && leftPosition < (tickGoal + STOP_THRESHOLD_TICKS)) ||
      (rightPosition > (tickGoal - STOP_THRESHOLD_TICKS) && rightPosition < (tickGoal + STOP_THRESHOLD_TICKS))) {
      onTargetCount++;
    } else {
      onTargetCount = 0;
    }

    // the arms have reached the desired position +/- the given threshold for at least ON_TARGET_MINIMUM_SECONDS
    if (onTargetCount > ON_TARGET_MINIMUM_COUNT) {
      runMotionMagic = false;
      drivingUp = false;
      drivingDown = false;
      if (armState % 2 == 1) {
        armsAreUp = true;
        armsAreDown = false;
      } else if (armState % 2 == 0) {
        armsAreUp = false;
        armsAreDown = true;
      }
    } else { // when the arms are not within the desired position +/- the given threshold
      runMotionMagic = true;
      armsAreUp = false;
      armsAreDown = false;
      if (armState % 2 == 1) {
        drivingUp = true;
        drivingDown = false;
      } else if (armState % 2 == 0) {
        drivingUp = false;
        drivingDown = true;
      }
    }

    Motors.leftIntakeArm.config_kP(pidIdx, driveP, timeoutMs);
    Motors.rightIntakeArm.config_kP(pidIdx, driveP, timeoutMs);
    
    // Motors.leftIntakeArm.config_kI(pidIdx, driveI, timeoutMs);
    // Motors.rightIntakeArm.config_kI(pidIdx, driveI, timeoutMs);
    
    // Motors.leftIntakeArm.config_kD(pidIdx, driveD, timeoutMs);
    // Motors.rightIntakeArm.config_kD(pidIdx, driveD, timeoutMs);
    
    Motors.leftIntakeArm.config_kF(pidIdx, driveF, timeoutMs);
    Motors.rightIntakeArm.config_kF(pidIdx, driveF, timeoutMs);

    SmartDashboard.putNumber("Arm Tick Current Goal", tickGoal);

    if (runMotionMagic) {
      SmartDashboard.putBoolean("Arm Motion Magic is Running", true);
      Motors.rightIntakeArm.set(ControlMode.MotionMagic, tickGoal);
      Motors.leftIntakeArm.set(ControlMode.MotionMagic, tickGoal);
    } else {
      SmartDashboard.putBoolean("Arm Motion Magic is Running", false);
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
    return false;
  }
}
