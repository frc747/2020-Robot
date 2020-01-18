package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

    public TalonFX leftDrivePrimary = new TalonFX(15);

	public TalonFX leftDriveBack = new TalonFX(14);

    public TalonFX rightDrivePrimary = new TalonFX(0);

    public TalonFX rightDriveBack = new TalonFX(1);

    public TalonSRX gearShifter = new TalonSRX(6);

    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;

    private static final double ENCODER_TICKS = 2048;

    private static final double GEAR_RATIO_MULTIPLIER = 13.85; //low gear

    private static final double WHEEL_DIAMETER = 6.18;

    private static final double WHEEL_CIRCUMFERNCE = WHEEL_DIAMETER*Math.PI;

    public static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;

    // Odometry class for tracking robot pose
    private final DifferentialDriveOdometry m_odometry;

	StringBuilder sb = new StringBuilder();
	int loops = 0;

    public DriveSubsystem() {

        super();

        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(PigeonSubsystem.getPigeonAngle()));

        gearShifter.setInverted(false);
        gearShifter.setSensorPhase(true);

        leftDrivePrimary.setInverted(true);
        leftDriveBack.setInverted(true);

        rightDrivePrimary.setInverted(false);
        rightDriveBack.setInverted(false);

        leftDriveBack.set(ControlMode.Follower, leftDrivePrimary.getDeviceID());
        rightDriveBack.set(ControlMode.Follower, rightDrivePrimary.getDeviceID());

        leftDrivePrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.IntegratedSensor, pidIdx, timeoutMs);
        rightDrivePrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.IntegratedSensor, pidIdx, timeoutMs);

        
        gearShifter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);

        leftDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
        leftDrivePrimary.configMotionAcceleration(20500, timeoutMs);
        rightDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
        rightDrivePrimary.configMotionAcceleration(20000, timeoutMs);

        leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);

        leftDriveBack.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDriveBack.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDriveBack.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        leftDriveBack.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDriveBack.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDriveBack.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDriveBack.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDriveBack.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);


        gearShifter.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        gearShifter.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        gearShifter.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        gearShifter.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);

    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftDrivePrimary.getSelectedSensorVelocity(), rightDrivePrimary.getSelectedSensorVelocity());
    }

    @Override
    public void periodic() {
        //                                                                    1/2048    6.18pi in meters   1/13.85
        m_odometry.update(Rotation2d.fromDegrees(-PigeonSubsystem.getPigeonAngle()), 0.00048828125*0.156972*Math.PI*undoGearRatio(leftDrivePrimary.getSelectedSensorPosition()),0.00048828125*0.156972*Math.PI*undoGearRatio(rightDrivePrimary.getSelectedSensorPosition()));
    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
      }    

    public void resetOdometry(Pose2d pose) {
        resetBothEncoders();
        m_odometry.resetPosition(pose, Rotation2d.fromDegrees(-PigeonSubsystem.getPigeonAngle()));
    }

    public void updateSpeeds() {
        leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    }

    public void set(double left, double right) {

        leftDriveBack.set(ControlMode.Follower, leftDrivePrimary.getDeviceID());
        rightDriveBack.set(ControlMode.Follower, rightDrivePrimary.getDeviceID());

        leftDrivePrimary.set(ControlMode.PercentOutput, left);
        rightDrivePrimary.set(ControlMode.PercentOutput, right);
    }

    public void setPID(double leftTicks, double rightTicks) {
        
        leftDriveBack.set(ControlMode.Follower, leftDrivePrimary.getDeviceID());
        rightDriveBack.set(ControlMode.Follower, rightDrivePrimary.getDeviceID());

        

        leftDrivePrimary.set(ControlMode.MotionMagic, leftTicks);
        rightDrivePrimary.set(ControlMode.MotionMagic, rightTicks);
    }

    public double convertRevsToInches(double revs) {
        return revs * WHEEL_CIRCUMFERNCE;
    }

    public double convertInchesToRevs(double inches) {
        return inches / WHEEL_CIRCUMFERNCE;
    }

    public double convertRevsToTicks(double revs) {
        return revs * ENCODER_TICKS;
    }

    public double convertTicksToRevs(double ticks) {
        return ticks / ENCODER_TICKS;
    }

    public double convertInchesToTicks(double inches) {
        return convertRevsToTicks(convertInchesToRevs(inches));
    }

    public double convertTicksToInches(double ticks) {
        return convertRevsToInches(convertTicksToRevs(ticks));
    }

    public double applyGearRatio(double original) {
        return original * GEAR_RATIO_MULTIPLIER;
    }

    public double undoGearRatio(double original) {
        return original / GEAR_RATIO_MULTIPLIER;
    }

    public double averageInchesDriven() {
        return convertTicksToInches(undoGearRatio(getCombindedEncoderPosition()));
    }

    public void changeControlMode(ControlMode mode) {
    	leftDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	rightDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
        leftDrivePrimary.set(mode, 0);
        rightDrivePrimary.set(mode, 0);
    }

    public void stop() {
        ControlMode mode = leftDrivePrimary.getControlMode();

        double left = 0;
        double right = 0;

        switch (mode) {
        case MotionMagic:
            left = leftDrivePrimary.getSelectedSensorPosition(pidIdx);
            right = rightDrivePrimary.getSelectedSensorPosition(pidIdx);
            break;
        case PercentOutput:
        case Velocity:
        case Follower:
        default:
            // Values should be 0;
            break;
        }

        this.set(left, right);
    }

    public void changeDriveBrakeMode(boolean enabled) {
        if (enabled) {
          leftDrivePrimary.setNeutralMode(NeutralMode.Brake);
          leftDriveBack.setNeutralMode(NeutralMode.Brake);
          rightDrivePrimary.setNeutralMode(NeutralMode.Brake);
          rightDriveBack.setNeutralMode(NeutralMode.Brake);
        } else {
            leftDrivePrimary.setNeutralMode(NeutralMode.Coast);
            leftDriveBack.setNeutralMode(NeutralMode.Coast);
            rightDrivePrimary.setNeutralMode(NeutralMode.Coast);
            rightDriveBack.setNeutralMode(NeutralMode.Coast);
        }
    
      }

    public void enablePositionControl() {
        this.changeControlMode(ControlMode.MotionMagic);
    }

    public void enableVBusControl() {
        this.changeControlMode(ControlMode.PercentOutput);
    }

    public void resetLeftEncoder() {
        this.enableVBusControl();
        leftDrivePrimary.setSelectedSensorPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    public void resetRightEncoder() {
        this.enableVBusControl();
        rightDrivePrimary.setSelectedSensorPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    public void resetBothEncoders(){
        this.enableVBusControl();
    	this.rightDrivePrimary.setSelectedSensorPosition(0);
    	this.leftDrivePrimary.setSelectedSensorPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

     //get the current encoder position regardless of whether it is the current feedback device
    public double getLeftPosition() {
        return undoGearRatio(leftDrivePrimary.getSelectedSensorPosition());
        
    }

    public double getRightPosition() {
        return undoGearRatio(rightDrivePrimary.getSelectedSensorPosition());
        
    }

    public double getCombindedEncoderPosition() {
        return (getLeftPosition() + getRightPosition()) / 2;
    }

}
