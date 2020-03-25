/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Motors;

/**
 * This subsystem controls all functionality related to the climb
 */
public class ClimbSubsystem extends SubsystemBase {
 
  int SAFE_MAXIMUM;
  int SAFE_MINIMUM;

  public ClimbSubsystem() {
    /* the encoder for the climb is wired into the indexer to allow for a
     mag encoder to be used instead of the pre installed encoder in the NEO */

    Motors.indexer.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    Motors.climbRight.follow(Motors.climbLeft);
    Motors.climbLeft.setSmartCurrentLimit(40);
    Motors.climbRight.setSmartCurrentLimit(40);
  }

  @Override
  public void periodic() {
    if(Motors.indexer.getSelectedSensorPosition() < SAFE_MINIMUM || Motors.indexer.getSelectedSensorPosition() > SAFE_MAXIMUM) {

    }
  }

  public double getClimbEncoder() {
    return Motors.indexer.getSelectedSensorPosition();
  }

  public void set(double speed) {
    Motors.climbLeft.set(speed);
  }

  public void stop() {
    set(0);  
  }
}
