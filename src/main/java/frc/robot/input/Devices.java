/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.input;

/**
 * This class contains public definitions for all of your input devices
 */
public class Devices {
  public static LogitechJoystick leftStick = new LogitechJoystick(0);
  public static LogitechJoystick rightStick = new LogitechJoystick(1);
  public static XboxController xboxController = new XboxController(2);
  public static GenesisController genesisController = new GenesisController(3);
}