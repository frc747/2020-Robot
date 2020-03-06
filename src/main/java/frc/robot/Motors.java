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
    
    public static TalonFX leftDrivePrimary = new TalonFX(0); // left front

    public static TalonFX leftDriveBack = new TalonFX(1); // left back

    //public static TalonSRX motorTwo = new TalonSRX(2); // empty

    //public static TalonFX motorThree = new TalonSRX(3); // empty

    public static TalonFX leftIntakeArm = new TalonFX(4); // left intake arm

    public static TalonSRX hood = new TalonSRX(5); // alleron

    //public static TalonSRX motorSix = new TalonSRX(6); // empty

    //public static TalonSRX motorSeven = new TalonSRX(7); // limelight

    public static TalonSRX transfer = new TalonSRX(8); // transfer

    public static TalonSRX indexer = new TalonSRX(9); // indexer

    public static TalonSRX intake = new TalonSRX(10); // intake rollers

    public static TalonFX rightIntakeArm = new TalonFX(11); // right intake arm

    //public static TalonSRX motorTwelve = new TalonSRX(12); // empty

    public static TalonFX shooter = new TalonFX(13); // shooter

    public static TalonFX rightDriveBack = new TalonFX(14); // right back
    
    public static TalonFX rightDrivePrimary = new TalonFX(15); // front right

    public static TalonFX leftDriveBack = new TalonFX(1);

    public static TalonFX leftDrivePrimary = new TalonFX(0);

}
