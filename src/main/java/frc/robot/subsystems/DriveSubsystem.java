package frc.robot.subsystems;

import frc.robot.Motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;

    private static final double ticksPerInch = 2048/(Math.PI*6);//.125);

    public static final double scalar = 1/13.85;

    private static final double ENCODER_TICKS = 2048;

    private static final double GEAR_RATIO_MULTIPLIER = 1;

    private static final double WHEEL_CIRCUMFERNCE = 20.125;

    public static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;

    public static double ROBOT_LENGTH; // CHANGE THESE
    public static double ROBOT_WIDTH; // CHANGE THESE

    public boolean tracking = true;

	StringBuilder sb = new StringBuilder();
	int loops = 0;

    public DriveSubsystem() {

        super();

        Motors.leftDrivePrimary.setInverted(true);
        Motors.leftDriveBack.setInverted(true);

        Motors.rightDrivePrimary.setInverted(false);
        Motors.rightDriveBack.setInverted(false);

        Motors.leftDriveBack.set(ControlMode.Follower, Motors.leftDrivePrimary.getDeviceID());
        Motors.rightDriveBack.set(ControlMode.Follower, Motors.rightDrivePrimary.getDeviceID());

        Motors.leftDrivePrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.IntegratedSensor, pidIdx, timeoutMs);
        Motors.rightDrivePrimary.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.IntegratedSensor, pidIdx, timeoutMs);

        Motors.leftDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
        Motors.leftDrivePrimary.configMotionAcceleration(20500, timeoutMs);
        Motors.rightDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
        Motors.rightDrivePrimary.configMotionAcceleration(20000, timeoutMs);

        Motors.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);

        Motors.leftDriveBack.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDriveBack.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDriveBack.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDriveBack.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDriveBack.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDriveBack.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDriveBack.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDriveBack.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);

    }

    public void updateSpeeds() {
        Motors.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    }

    public void set(double left, double right) {

        Motors.leftDriveBack.set(ControlMode.Follower, Motors.leftDrivePrimary.getDeviceID());
        Motors.rightDriveBack.set(ControlMode.Follower, Motors.rightDrivePrimary.getDeviceID());

        Motors.leftDrivePrimary.set(ControlMode.PercentOutput, left);
        Motors.rightDrivePrimary.set(ControlMode.PercentOutput, right);
    }

    public void setPID(double leftTicks, double rightTicks) {
        
        Motors.leftDriveBack.set(ControlMode.Follower, Motors.leftDrivePrimary.getDeviceID());
        Motors.rightDriveBack.set(ControlMode.Follower, Motors.rightDrivePrimary.getDeviceID());

        

        Motors.leftDrivePrimary.set(ControlMode.MotionMagic, leftTicks);
        Motors.rightDrivePrimary.set(ControlMode.MotionMagic, rightTicks);
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
        return ticksPerInch*inches*13.58;
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
    	Motors.leftDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
    	Motors.rightDrivePrimary.setSelectedSensorPosition(0, pidIdx, timeoutMs);
        Motors.leftDrivePrimary.set(mode, 0);
        Motors.rightDrivePrimary.set(mode, 0);
    }

    public void stop() {
        ControlMode mode = Motors.leftDrivePrimary.getControlMode();

        double left = 0;
        double right = 0;

        switch (mode) {
        case MotionMagic:
            left = Motors.leftDrivePrimary.getSelectedSensorPosition(pidIdx);
            right = Motors.rightDrivePrimary.getSelectedSensorPosition(pidIdx);
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
          Motors.leftDrivePrimary.setNeutralMode(NeutralMode.Brake);
          Motors.leftDriveBack.setNeutralMode(NeutralMode.Brake);
          Motors.rightDrivePrimary.setNeutralMode(NeutralMode.Brake);
          Motors.rightDriveBack.setNeutralMode(NeutralMode.Brake);
        } else {
            Motors.leftDrivePrimary.setNeutralMode(NeutralMode.Coast);
            Motors.leftDriveBack.setNeutralMode(NeutralMode.Coast);
            Motors.rightDrivePrimary.setNeutralMode(NeutralMode.Coast);
            Motors.rightDriveBack.setNeutralMode(NeutralMode.Coast);
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
        Motors.leftDrivePrimary.setSelectedSensorPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void resetRightEncoder() {
        this.enableVBusControl();
        Motors.rightDrivePrimary.setSelectedSensorPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void resetBothEncoders(){
        this.enableVBusControl();
    	Motors.rightDrivePrimary.setSelectedSensorPosition(0);
    	Motors.leftDrivePrimary.setSelectedSensorPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
        //get the current encoder position regardless of whether it is the current feedback device
    public double getLeftAbsolutePosition() {
        return Motors.leftDrivePrimary.getSelectedSensorPosition()*scalar;
        
    }

    public double getRightAbsolutePosition() {
        return Motors.rightDrivePrimary.getSelectedSensorPosition()*scalar;
        
    }

    public double getLeftPosition() {
        return Motors.leftDrivePrimary.getSelectedSensorPosition()*scalar;
        
    }

    public double getRightPosition() {
        return Motors.rightDrivePrimary.getSelectedSensorPosition()*scalar;
        
    }

    public double getLeftVelocity() {
        return Motors.leftDrivePrimary.getSelectedSensorVelocity();
    }

    public double getRightVelocity() {
        return Motors.rightDrivePrimary.getSelectedSensorVelocity();
    }

    //Returns the average of the absolute value of the left and right side velocities. Used to find average speed of both sides, not to find if the robot is moving.
    public double getAverageVelocity() {
        return (Math.abs(getLeftVelocity()) + Math.abs(getRightVelocity()))/2;
    }
    public double getCombindedEncoderPosition() {
        return (getLeftPosition() + getRightPosition()) / 2;
    }

}
