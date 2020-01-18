/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.List;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Robot;
import frc.robot.maps.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class FollowTrajectory extends CommandBase {
  /**
   * Creates a new RamseteCommand.
   */
  public FollowTrajectory() {
    var autoVoltageConstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter, Constants.kaVoltSecondsSquaredPerMeter), Constants.kDriveKinematics, 10);
    
    TrajectoryConfig config = new TrajectoryConfig(Constants.kMaxSpeedMetersPerSecond, Constants.kMaxAccelerationMetersPerSecondSquared).setKinematics(Constants.kDriveKinematics).addConstraint(autoVoltageConstraint);
    // Use addRequirements() here to declare subsystem dependencies.
    
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)), List.of(new Translation2d(1, 1), new Translation2d(2, -1)), new Pose2d(3, 0, new Rotation2d(0)), config);
    
    RamseteCommand ramseteCommand = new RamseteCommand(
      exampleTrajectory, 
      Robot.m_driveSubsystem::getPose, 
      new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta), 
      new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter, Constants.kaVoltSecondsSquaredPerMeter), 
      Constants.kDriveKinematics, 
      Robot.m_driveSubsystem::getWheelSpeeds, 
      new PIDController(Constants.kP, 0, 0), 
      new PIDController(Constants.kP, 0, 0), 
      Robot.m_driveSubsystem::set, 
      Robot.m_driveSubsystem);

      ramseteCommand.andThen(() -> Robot.m_driveSubsystem.set(0,0));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
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
