/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class OperatorController {

  private static Joystick operatorController;

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

  public OperatorController(int id) {
    operatorController = new Joystick(id);
    BUTTON_A = new JoystickButton(operatorController, Buttons.A);
    BUTTON_B = new JoystickButton(operatorController, Buttons.B);
    BUTTON_X = new JoystickButton(operatorController, Buttons.X);
    BUTTON_Y = new JoystickButton(operatorController, Buttons.Y);
    BUTTON_LEFT_BUMPER = new JoystickButton(operatorController, Buttons.LeftBumper);
    BUTTON_RIGHT_BUMPER = new JoystickButton(operatorController, Buttons.RightBumper);
    BUTTON_SELECT = new JoystickButton(operatorController, Buttons.Select);
    BUTTON_START = new JoystickButton(operatorController, Buttons.Start);
    BUTTON_LEFT_STICK = new JoystickButton(operatorController, Buttons.LeftStick);
    BUTTON_RIGHT_STICK = new JoystickButton(operatorController, Buttons.RightStick);
  }

  public boolean getA() {
    return operatorController.getRawButton(Buttons.A);
  }

  public boolean getB() {
    return operatorController.getRawButton(Buttons.B);
  }
  
  public boolean getX() {
    return operatorController.getRawButton(Buttons.A);
  }

  public boolean getY() {
    return operatorController.getRawButton(Buttons.Y);
  }

  public boolean getLeftBumper() {
    return operatorController.getRawButton(Buttons.LeftBumper);
  }

  public boolean getRightBumper() {
    return operatorController.getRawButton(Buttons.RightBumper);
  }

  public boolean getSelect() {
    return operatorController.getRawButton(Buttons.Select);
  }

  public boolean getStart() {
    return operatorController.getRawButton(Buttons.Start);
  }

  public boolean getLeftStick() {
    return operatorController.getRawButton(Buttons.LeftStick);
  }

  public boolean getRightStick() {
    return operatorController.getRawButton(Buttons.RightStick);
  }

  public double getLeftX() {
    return operatorController.getRawAxis(Axes.LeftX);
  }

  public double getRightX() {
    return operatorController.getRawAxis(Axes.RightX);
  }

  public double getLeftY() {
    return operatorController.getRawAxis(Axes.LeftY);
  }

  public double getRightY() {
    return operatorController.getRawAxis(Axes.RightY);
  }

  public double getLeftTrigger() {
    return operatorController.getRawAxis(Axes.LeftTrigger);
  }

  public double getRightTrigger() {
    return operatorController.getRawAxis(Axes.RightTrigger);
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
    public static final int RightX = 3;
    public static final int RightY = 4;
    public static final int RightTrigger = 5;
  }

}
