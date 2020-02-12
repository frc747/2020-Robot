/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.hal.I2CJNI;

public class LIDARSubsystem extends SubsystemBase {

  private static final byte mDeviceAddress = 0x62;

  private final byte mPort;

  private final ByteBuffer mBuffer = ByteBuffer.allocateDirect(2);

  public LIDARSubsystem(Port port) {
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
    return (0.393701 * readShort(0x8F) - 6.5);
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

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
