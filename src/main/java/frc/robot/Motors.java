/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class Motors {

    public static TalonFX rightDrivePrimary = new TalonFX(15);

    public static TalonFX rightDriveBack = new TalonFX(14);

    public static TalonSRX transfer = new TalonSRX(2);

    public static TalonFX rightIntakeArm = new TalonFX(3);

    public static TalonSRX indexer = new TalonSRX(4);

    //public static TalonSRX motorFive = new TalonSRX(5);

    //public static TalonSRX motorSix = new TalonSRX(6);

    //public static TalonSRX motorSeven = new TalonSRX(7);

    //public static TalonSRX motorEight = new TalonSRX(8);

    public static TalonSRX intake = new TalonSRX(9);

    public static TalonSRX hood = new TalonSRX(10);

    //public static TalonSRX motorEleven = new TalonSRX(11);

    public static TalonFX leftIntakeArm = new TalonFX(12);

    public static TalonFX shooter = new TalonFX(13);

    public static TalonFX leftDriveBack = new TalonFX(1);

    public static TalonFX leftDrivePrimary = new TalonFX(0);

}
