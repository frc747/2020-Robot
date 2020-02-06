/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {

  private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  
  public static boolean tracking = false;

  public enum ledMode {
    USE_PIPELINE(0),
    OFF(1),
    BLINK(2),
    ON(3);
    public final int state;
    private ledMode(int mode) {
      this.state = mode;
    }
  }

  public enum camMode {
    VISION_PROCESSOR(0),
    DRIVER_CAMERA(1);
    public final int state;
    private camMode(int mode) {
      this.state = mode;
    }
  }

  public enum streamingMode {
    STANDARD(0),
    PiP_MAIN(1),
    PiP_SECONDARY(2);
    public final int state;
    private streamingMode(int mode) {
      this.state = mode;
    }
  }

  public LimelightSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public static boolean validTargets() {
    if (getEntry("tv") == 1.0) {
      return true;
    } else {
      return false;
    }
  }

  public static double getHorizontalOffset() { /** LL1: -27 degrees to 27 degrees */
    return getEntry("tx");
  }

  public static double getVerticalOffset() { /** LL1: -20.5 degrees to 20.5 degrees */
    return getEntry("ty");
  }

  public static double getArea() { /** 0% of image to 100% of image */
    return getEntry("ta");
  }

  public static double getSkew() { /** -90 degrees to 0 degrees */
    return getEntry("ts");
  }

  public static double getLatency() { /** The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency. */
    return getEntry("tl");
  }

  public static double getColor() {
    return getEntry("tc");
  }

  public static double getBoundingBoxShortLength() { /** Sidelength of shortest side of the fitted bounding box (pixels) */
    return getEntry("tshort");
  }

  public static double getBoundingBoxLongLength() { /** Sidelength of longest side of the fitted bounding box (pixels) */
    return getEntry("tlong");
  }

  public static double thor() { /** Horizontal sidelength of the rough bounding box (0 - 320 pixels) */
    return getEntry("tshort");
  }

  public static double tvert() { /** Vertical sidelength of the rough bounding box (0 - 320 pixels) */
    return getEntry("tlong");
  }

  public static double getPipeline() { /** True active pipeline index of the camera (0 .. 9) */
    return getEntry("getpipe");
  }

  public static void setLEDMode(ledMode mode) {
    setEntry("ledMode", mode.state);
  }

  public static void setCamMode(camMode mode) {
    setEntry("camMode", mode.state);
  }

  public static void setPipeline(int pipeline) { /** True active pipeline index of the camera (0 .. 9) */
    setEntry("pipeline", pipeline);
  }

  public static void setStreamingMode(streamingMode mode) { /** True active pipeline index of the camera (0 .. 9) */
    setEntry("pipeline", mode.state);
  }

  public static void setSnapshots(boolean snapshot) {
    if (snapshot) {
      setEntry("snapshot", 1);
    } else {
      setEntry("snapshot", 0);
    }
  } 

  private static void setEntry(String key, int value) {
    table.getEntry(key).setDouble(value);
  }
  
  private static double getEntry(String key) {
    return table.getEntry(key).getDouble(0);
  }

}
