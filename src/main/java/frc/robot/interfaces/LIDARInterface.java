/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.interfaces;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Motors;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.hal.I2CJNI;

public class LIDARInterface extends SubsystemBase {


  private double savedDistance;
  boolean firstStart = true;
  boolean isRobotMovingCheck = true;
  private double leftSpeed = 0;
  private double rightSpeed = 0;

  private static final byte mDeviceAddress = 0x62;

  private final byte mPort;

  private final ByteBuffer mBuffer = ByteBuffer.allocateDirect(2);

  public LIDARInterface(Port port) {
    firstStart = true;
    mPort = (byte) port.value;
    I2CJNI.i2CInitialize(mPort);
  }

  public void startMeasuring() {
    writeRegister(0x04, 0x08 | 32);
    writeRegister(0x11, 0xFF);
    writeRegister(0x00, 0x04);
  }
  
  public void stopMeasuring() {
    writeRegister(0x11, 0x00);
  }

  public double getDistance() {
    if(firstStart) {
      savedDistance = (0.393701 * readShort(0x8F) - 6.5);
      firstStart = false;
    }

    if(isRobotMoving()) {
      isRobotMovingCheck = true;
      return (0.393701 * readShort(0x8F) - 6.5);
    } else {
      if(isRobotMovingCheck) {
        savedDistance = (0.393701 * readShort(0x8F) - 6.5);
        isRobotMovingCheck = false;
      }
      return savedDistance;
    }
  }

  public boolean checkLidar() {
    double check = (0.393701 * readShort(0x8F) - 6.5);
    return check > 0;
  }

  private int writeRegister(int address, int value) {
    mBuffer.put(0, (byte) address);
    mBuffer.put(1, (byte) value);

    return I2CJNI.i2CWrite(mPort, mDeviceAddress, mBuffer, (byte) 2);
  }

  private int readShort(int address) {
    mBuffer.put(0, (byte) address);
    I2CJNI.i2CWrite(mPort, mDeviceAddress, mBuffer, (byte) 1);
    I2CJNI.i2CRead(mPort, mDeviceAddress, mBuffer, (byte) 2);
    return mBuffer.getShort(0);
  }

  public boolean isRobotMoving() {
    leftSpeed = Math.abs(Motors.leftDrivePrimary.getSelectedSensorVelocity());
    rightSpeed = Math.abs(Motors.rightDrivePrimary.getSelectedSensorVelocity());
    return (leftSpeed > 50 || rightSpeed > 50);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
