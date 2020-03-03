/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechJoystick {

  private Joystick logitechJoystick;

  public LogitechJoystick(int id) {
    logitechJoystick = new Joystick(id);
  }

  public double getX() {
    return logitechJoystick.getRawAxis(Axes.X);
  }

  public double getY() {
    return logitechJoystick.getRawAxis(Axes.Y);
  }

  public double getZ() {
    return logitechJoystick.getRawAxis(Axes.Z);
  }

  public boolean getTrigger() {
    return logitechJoystick.getRawButton(Buttons.Trigger);
  }

  public boolean getThumb() {
    return logitechJoystick.getRawButton(Buttons.Thumb);
  }

  public boolean getStickBottomLeft() {
    return logitechJoystick.getRawButton(Buttons.StickBottomLeft);
  }

  public boolean getStickBottomRight() {
    return logitechJoystick.getRawButton(Buttons.StickBottomRight);
  }
  
  public boolean getStickTopLeft() {
    return logitechJoystick.getRawButton(Buttons.StickTopLeft);
  }

  public boolean getStickTopRight() {
    return logitechJoystick.getRawButton(Buttons.StickTopRight);
  }

  public boolean getBaseTopLeft() {
    return logitechJoystick.getRawButton(Buttons.BaseTopLeft);
  }

  public boolean getBaseTopRight() {
    return logitechJoystick.getRawButton(Buttons.BaseTopRight);
  }

  public boolean getBaseMidLeft() {
    return logitechJoystick.getRawButton(Buttons.BaseMidLeft);
  }

  public boolean getBaseMidRight() {
    return logitechJoystick.getRawButton(Buttons.BaseMidRight);
  }

  public boolean getBaseBottomLeft() {
    return logitechJoystick.getRawButton(Buttons.BaseBottomLeft);
  }

  public boolean getBaseBottomRight() {
    return logitechJoystick.getRawButton(Buttons.BaseBottomRight);
  }

  public static final class Buttons {
    public static final int Trigger = 1;
    public static final int Thumb = 2;
    public static final int StickBottomLeft = 3;
    public static final int StickBottomRight = 4;
    public static final int StickTopLeft = 5;
    public static final int StickTopRight = 6;
    public static final int BaseTopLeft = 7;
    public static final int BaseTopRight = 8;
    public static final int BaseMidLeft = 9;
    public static final int BaseMidRight = 10;
    public static final int BaseBottomLeft = 11;
    public static final int BaseBottomRight = 12;

  }

  public static final class Axes {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    public static final int Slider = 3;
  }

}
