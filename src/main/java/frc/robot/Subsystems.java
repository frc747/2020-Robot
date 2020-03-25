/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.HoodToAngle;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RunIndexer;
import frc.robot.commands.RunShooter;
import frc.robot.commands.RunTransfer;
import frc.robot.commands.ServoAngle;
import frc.robot.commands.TankDriveCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.HoodSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightServoSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.TransferSubsystem;

/**
 * This class contains public definitions of all of your Subsystems
 */
public class Subsystems {

  public static final DriveSubsystem Drive = new DriveSubsystem();
  public static final ShooterSubsystem Shooter = new ShooterSubsystem();
  public static final TransferSubsystem Transfer = new TransferSubsystem();
  public static final HoodSubsystem Hood = new HoodSubsystem();
  public static final IntakeSubsystem Intake = new IntakeSubsystem();
  public static final IndexerSubsystem Indexer = new IndexerSubsystem();
  public static final LimelightServoSubsystem LimelightServo = new LimelightServoSubsystem();
  public static final ClimbSubsystem Climb = new ClimbSubsystem();
  
  
  /**
   * Sets the default commands for all of your defined subsystems.
   * Must be called in robotInit() in Robot.java
   */
  public static void setDefaultCommands() {
    Subsystems.Drive.setDefaultCommand(new TankDriveCommand());
    Subsystems.Shooter.setDefaultCommand(new RunShooter());
    Subsystems.Transfer.setDefaultCommand(new RunTransfer());
    Subsystems.Hood.setDefaultCommand(new HoodToAngle());
    Subsystems.Intake.setDefaultCommand(new IntakeCommand());
    Subsystems.Indexer.setDefaultCommand(new RunIndexer());
    Subsystems.LimelightServo.setDefaultCommand(new ServoAngle());
  }
}
