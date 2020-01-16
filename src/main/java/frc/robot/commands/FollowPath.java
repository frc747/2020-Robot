/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

import jaci.pathfinder.Trajectory;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.followers.EncoderFollower;


public class FollowPath extends CommandBase {

  String pathName;
  double ticksPerRev = Robot.DRIVE_SUBSYSTEM.convertRevsToTicks(1);

  double maxVelocity = 10;

  private EncoderFollower leftEncoderFollower;
  private EncoderFollower rightEncoderFollower;

  boolean targetReached = false;

  public FollowPath(String name) {
    addRequirements(Robot.DRIVE_SUBSYSTEM);

    this.pathName = name;
    

    try {
      Trajectory left_trajectory = PathfinderFRC.getTrajectory(pathName + ".left");
      Trajectory right_trajectory = PathfinderFRC.getTrajectory(pathName + ".right");

      leftEncoderFollower = new EncoderFollower(left_trajectory);
      rightEncoderFollower = new EncoderFollower(right_trajectory);

      leftEncoderFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / maxVelocity, 0);
      rightEncoderFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / maxVelocity, 0);

    
    } catch(IOException e) {
      System.out.println("TRAJECTORY NOT FOUND!");
      e.printStackTrace();
    }

    

  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {

    if(leftEncoderFollower.isFinished() || rightEncoderFollower.isFinished()) {
      targetReached = true;
    }

    double left_speed = leftEncoderFollower.calculate((int)Robot.DRIVE_SUBSYSTEM.getLeftPosition());
    double right_speed = rightEncoderFollower.calculate((int)Robot.DRIVE_SUBSYSTEM.getRightPosition());
    double heading = Robot.getPigeonAngle();
    double desired_heading = Pathfinder.r2d(leftEncoderFollower.getHeading());
    double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
    double turn =  0.8 * (-1.0/80.0) * heading_difference;
    Robot.DRIVE_SUBSYSTEM.set(left_speed + turn, right_speed - turn);

  }

  @Override
  public void end(boolean interrupted) {
    Robot.DRIVE_SUBSYSTEM.stop();
  }

  @Override
  public boolean isFinished() {
    return targetReached;
  }
}