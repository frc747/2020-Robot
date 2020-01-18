package frc.robot;

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

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.*;

import frc.robot.autonomous.*;
import frc.robot.commands.FollowTrajectory;
import frc.robot.commands.PIDDriveInches;
import frc.robot.maps.Constants;
import frc.robot.subsystems.LimelightSubsystem;

public class Autonomous{
    
    public enum AutoMode{
        AUTOMODE_EXAMPLE,
        TRAJECTORY_COMMAND
    }
    
    private SendableChooser<AutoMode> autoChooser1;

    public Autonomous() {
        autoChooser1 = new SendableChooser<AutoMode>();

        autoChooser1.setDefaultOption("Trajectory mapping", AutoMode.TRAJECTORY_COMMAND);
        SmartDashboard.putData("Auto mode", autoChooser1);
    }

    public void startMode() {

        System.out.println("In Auto.StartMode");

        AutoMode selectedAutoMode = (AutoMode) (autoChooser1.getSelected());
                    
        switch (selectedAutoMode){
            case AUTOMODE_EXAMPLE:
                LimelightSubsystem.setPipeline(1);
                new FollowTrajectory().schedule();
                break;
            case TRAJECTORY_COMMAND:
                new RamseteCommand(
                    TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)), List.of(new Translation2d(1, 1), new Translation2d(2, -1)), new Pose2d(3, 0, new Rotation2d(0)), new TrajectoryConfig(Constants.kMaxSpeedMetersPerSecond, Constants.kMaxAccelerationMetersPerSecondSquared).setKinematics(Constants.kDriveKinematics).addConstraint(new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter, Constants.kaVoltSecondsSquaredPerMeter), Constants.kDriveKinematics, 10))), 
                    Robot.m_driveSubsystem::getPose, 
                    new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta), 
                    new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter, Constants.kaVoltSecondsSquaredPerMeter), 
                    Constants.kDriveKinematics, 
                    Robot.m_driveSubsystem::getWheelSpeeds, 
                    new PIDController(Constants.kP, 0, 0), 
                    new PIDController(Constants.kP, 0, 0), 
                    Robot.m_driveSubsystem::set, 
                    Robot.m_driveSubsystem).andThen(() -> Robot.m_driveSubsystem.set(0, 0));
            default:
                break;
        }

    }
}