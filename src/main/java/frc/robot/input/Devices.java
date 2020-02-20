/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.input;

public class Devices {
  public static DriverJoystick leftStick = new DriverJoystick(0);
  public static DriverJoystick rightStick = new DriverJoystick(1);
  public static OperatorController operatorController = new OperatorController(2);
}