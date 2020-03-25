/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Add your docs here.
 */
public class Motors {
    
    public static TalonFX leftDrivePrimary = new TalonFX(0); // left front

    public static TalonFX leftDriveBack = new TalonFX(1); // left back

    public static TalonFX shooter = new TalonFX(2); // shooter

    public static CANSparkMax climbRight = new CANSparkMax(3, MotorType.kBrushless);

    public static TalonFX leftIntakeArm = new TalonFX(4); // left intake arm

    public static TalonSRX hood = new TalonSRX(5); // aileron

    //public static TalonSRX motorSix = new TalonSRX(6); // empty

    //public static TalonSRX motorSeven = new TalonSRX(7); // limelight

    public static TalonSRX transfer = new TalonSRX(8); // transfer

    public static TalonSRX indexer = new TalonSRX(9); // indexer

    public static TalonSRX intake = new TalonSRX(10); // intake rollers

    public static TalonFX rightIntakeArm = new TalonFX(11); // right intake arm

    public static CANSparkMax climbLeft = new CANSparkMax(12, MotorType.kBrushless); 

    //public static TalonSRX motorThirteen = new TalonSRX(13); // empty

    public static TalonFX rightDriveBack = new TalonFX(14); // right back
    
    public static TalonFX rightDrivePrimary = new TalonFX(15); // front right


    public static void resetControllers() {
        leftDrivePrimary.configFactoryDefault();
        leftDriveBack.configFactoryDefault();
        //motorTwo.configFactoryDegfault();
        climbRight.restoreFactoryDefaults();
        leftIntakeArm.configFactoryDefault();
        hood.configFactoryDefault();
        //motorSix.configFactoryDefault();
        //motorSeven.configFactoryDefault();
        transfer.configFactoryDefault();
        indexer.configFactoryDefault();
        intake.configFactoryDefault();
        rightIntakeArm.configFactoryDefault();
        climbLeft.restoreFactoryDefaults();
        shooter.configFactoryDefault();
        rightDriveBack.configFactoryDefault();
        rightDriveBack.configFactoryDefault();
    }

}
