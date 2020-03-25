/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class implements all of the functionality of the Sega Genesis
 * USB Controller. 
 */
public class GenesisController {

  private Joystick genesisController;

  public JoystickButton BUTTON_X;
  public JoystickButton BUTTON_Y;
  public JoystickButton BUTTON_Z;
  public JoystickButton BUTTON_A;
  public JoystickButton BUTTON_B;
  public JoystickButton BUTTON_C;
  public JoystickButton BUTTON_LEFT_BUMPER;
  public JoystickButton BUTTON_RIGHT_BUMPER;
  public JoystickButton BUTTON_MODE;
  public JoystickButton BUTTON_START;

  public GenesisController(int id) {
    genesisController = new Joystick(id);
    BUTTON_A = new JoystickButton(genesisController, Buttons.A);
    BUTTON_B = new JoystickButton(genesisController, Buttons.B);
    BUTTON_C = new JoystickButton(genesisController, Buttons.C);
    BUTTON_X = new JoystickButton(genesisController, Buttons.X);
    BUTTON_Y = new JoystickButton(genesisController, Buttons.Y);
    BUTTON_Z = new JoystickButton(genesisController, Buttons.Z);
    BUTTON_LEFT_BUMPER = new JoystickButton(genesisController, Buttons.LeftBumper);
    BUTTON_RIGHT_BUMPER = new JoystickButton(genesisController, Buttons.RightBumper);
    BUTTON_MODE = new JoystickButton(genesisController, Buttons.Mode);
    BUTTON_START = new JoystickButton(genesisController, Buttons.Start);
  }

  public boolean getA() {
    return genesisController.getRawButton(Buttons.A);
  }

  public boolean getB() {
    return genesisController.getRawButton(Buttons.B);
  }

  public boolean getC() {
    return genesisController.getRawButton(Buttons.C);
  }
  
  public boolean getX() {
    return genesisController.getRawButton(Buttons.X);
  }

  public boolean getY() {
    return genesisController.getRawButton(Buttons.Y);
  }

  public boolean getZ() {
    return genesisController.getRawButton(Buttons.Z);
  }

  public boolean getLeftBumper() {
    return genesisController.getRawButton(Buttons.LeftBumper);
  }

  public boolean getRightBumper() {
    return genesisController.getRawButton(Buttons.RightBumper);
  }

  public boolean getMode() {
    return genesisController.getRawButton(Buttons.Mode);
  }

  public boolean getStart() {
    return genesisController.getRawButton(Buttons.Start);
  }

  public boolean getLeft() {
    return (genesisController.getRawAxis(Axes.DpadX) <= -0.9);
  }

  public boolean getRight() {
    return (genesisController.getRawAxis(Axes.DpadX) >= 0.9);
  }

  public boolean getUp() {
    return (genesisController.getRawAxis(Axes.DpadY) >= 0.9);
  }

  public boolean getDown() {
    return (genesisController.getRawAxis(Axes.DpadY) <= -0.9);
  }

  public int getPOV() {
    return genesisController.getPOV();
  }

  public static final class Buttons {
    public static final int A = 2;
    public static final int B = 3;
    public static final int C = 5;
    public static final int X = 1;
    public static final int Y = 4;
    public static final int Z = 6;
    public static final int LeftBumper = 7;
    public static final int RightBumper = 8;
    public static final int Mode = 9;
    public static final int Start = 10;
  }

  public static final class Axes {
    public static final int DpadX = 3;
    public static final int DpadY = 4;
  }

}
