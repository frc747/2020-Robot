/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class XboxController {

  private Joystick xboxController;

  public JoystickButton BUTTON_A;
  public JoystickButton BUTTON_B;
  public JoystickButton BUTTON_X;
  public JoystickButton BUTTON_Y;
  public JoystickButton BUTTON_LEFT_BUMPER;
  public JoystickButton BUTTON_RIGHT_BUMPER;
  public JoystickButton BUTTON_SELECT;
  public JoystickButton BUTTON_START;
  public JoystickButton BUTTON_LEFT_STICK;
  public JoystickButton BUTTON_RIGHT_STICK;

  public XboxController(int id) {
    xboxController = new Joystick(id);
    BUTTON_A = new JoystickButton(xboxController, Buttons.A);
    BUTTON_B = new JoystickButton(xboxController, Buttons.B);
    BUTTON_X = new JoystickButton(xboxController, Buttons.X);
    BUTTON_Y = new JoystickButton(xboxController, Buttons.Y);
    BUTTON_LEFT_BUMPER = new JoystickButton(xboxController, Buttons.LeftBumper);
    BUTTON_RIGHT_BUMPER = new JoystickButton(xboxController, Buttons.RightBumper);
    BUTTON_SELECT = new JoystickButton(xboxController, Buttons.Select);
    BUTTON_START = new JoystickButton(xboxController, Buttons.Start);
    BUTTON_LEFT_STICK = new JoystickButton(xboxController, Buttons.LeftStick);
    BUTTON_RIGHT_STICK = new JoystickButton(xboxController, Buttons.RightStick);
  }

  public boolean getA() {
    return xboxController.getRawButton(Buttons.A);
  }

  public boolean getB() {
    return xboxController.getRawButton(Buttons.B);
  }

  public boolean getX() {
    return xboxController.getRawButton(Buttons.X);
  }

  public boolean getY() {
    return xboxController.getRawButton(Buttons.Y);
  }

  public boolean getLeftBumper() {
    return xboxController.getRawButton(Buttons.LeftBumper);
  }

  public boolean getRightBumper() {
    return xboxController.getRawButton(Buttons.RightBumper);
  }

  public boolean getSelect() {
    return xboxController.getRawButton(Buttons.Select);
  }

  public boolean getStart() {
    return xboxController.getRawButton(Buttons.Start);
  }

  public boolean getLeftStick() {
    return xboxController.getRawButton(Buttons.LeftStick);
  }

  public boolean getRightStick() {
    return xboxController.getRawButton(Buttons.RightStick);
  }

  public double getLeftX() {
    return xboxController.getRawAxis(Axes.LeftX);
  }

  public double getRightX() {
    return xboxController.getRawAxis(Axes.RightX);
  }

  public double getLeftY() {
    return xboxController.getRawAxis(Axes.LeftY);
  }

  public double getRightY() {
    return xboxController.getRawAxis(Axes.RightY);
  }

  public double getLeftTrigger() {
    return xboxController.getRawAxis(Axes.LeftTrigger);
  }

  public double getRightTrigger() {
    return xboxController.getRawAxis(Axes.RightTrigger);
  }

  public static final class Buttons {
    public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
    public static final int Y = 4;
    public static final int LeftBumper = 5;
    public static final int RightBumper = 6;
    public static final int Select = 7;
    public static final int Start = 8;
    public static final int LeftStick = 9;
    public static final int RightStick = 10;
  }

  public static final class Axes {
    public static final int LeftX = 0;
    public static final int LeftY = 1;
    public static final int LeftTrigger = 2;
    public static final int RightTrigger = 3;
    public static final int RightX = 4;
    public static final int RightY = 5;
  }

}
