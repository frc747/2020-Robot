/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;

public class DriverJoystick {

  private Joystick driverJoystick;

  public DriverJoystick(int id) {
    driverJoystick = new Joystick(id);
  }

  public double getX() {
    return driverJoystick.getRawAxis(Axes.X);
  }

  public double getY() {
    return driverJoystick.getRawAxis(Axes.Y);
  }

  public double getZ() {
    return driverJoystick.getRawAxis(Axes.Z);
  }

  public boolean getTrigger() {
    return driverJoystick.getRawButton(Buttons.Trigger);
  }

  public boolean getThumb() {
    return driverJoystick.getRawButton(Buttons.Thumb);
  }

  public boolean getStickBottomLeft() {
    return driverJoystick.getRawButton(Buttons.StickBottomLeft);
  }

  public boolean getStickBottomRight() {
    return driverJoystick.getRawButton(Buttons.StickBottomRight);
  }
  
  public boolean getStickTopLeft() {
    return driverJoystick.getRawButton(Buttons.StickTopLeft);
  }

  public boolean getStickTopRight() {
    return driverJoystick.getRawButton(Buttons.StickTopRight);
  }

  public boolean getBaseTopLeft() {
    return driverJoystick.getRawButton(Buttons.BaseTopLeft);
  }

  public boolean getBaseTopRight() {
    return driverJoystick.getRawButton(Buttons.BaseTopRight);
  }

  public boolean getBaseMidLeft() {
    return driverJoystick.getRawButton(Buttons.BaseMidLeft);
  }

  public boolean getBaseMidRight() {
    return driverJoystick.getRawButton(Buttons.BaseMidRight);
  }

  public boolean getBaseBottomLeft() {
    return driverJoystick.getRawButton(Buttons.BaseBottomLeft);
  }

  public boolean getBaseBottomRight() {
    return driverJoystick.getRawButton(Buttons.BaseBottomRight);
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
