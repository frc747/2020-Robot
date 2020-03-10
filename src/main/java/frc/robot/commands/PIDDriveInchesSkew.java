package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Motors;
import frc.robot.OI;
import frc.robot.Subsystems;

public class PIDDriveInchesSkew extends CommandBase {
    private double driveTicks;
    private double driveInches;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;

    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;

    private final static double STOP_THRESHOLD_REAL = .1;
    private static double STOP_THRESHOLD_ADJUSTED = Subsystems.Drive.convertInchesToTicks(STOP_THRESHOLD_REAL);
    
    private final static int allowableCloseLoopError = 1;
    
    private int onTargetCount = 0;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .2;

    private double driveGoal;

    private double driveP = .5;
    
    private double driveI = 0;
    
    private double driveD = 0;
    
    private double driveF = 0;//.199;
    
    public PIDDriveInchesSkew(double inches, boolean reverse) {
        addRequirements(Subsystems.Drive);
        STOP_THRESHOLD_ADJUSTED*=Math.tanh(0.0625*inches);
        SmartDashboard.putNumber("THRESHOLD", STOP_THRESHOLD_ADJUSTED);
        if (reverse) {
            this.driveTicks = -Subsystems.Drive.convertInchesToTicks(inches);//input now has to be ticks instead of revolutions which is why we multiply by 4096
        } else {
            this.driveTicks = Subsystems.Drive.convertInchesToTicks(inches);
        }
        
        this.driveInches = inches;
    }
    
    @Override
    public void initialize() {
        
        onTargetCount = 0;
        
        Subsystems.Drive.resetBothEncoders();

        //Subsystems.Drive.enablePositionControl();
        
        Motors.leftDrivePrimary.config_kP(pidIdx, driveP, timeoutMs);
        Motors.rightDrivePrimary.config_kP(pidIdx, driveP, timeoutMs);
        
        Motors.leftDrivePrimary.config_kI(pidIdx, driveI, timeoutMs);
        Motors.rightDrivePrimary.config_kI(pidIdx, driveI, timeoutMs);
        
        Motors.leftDrivePrimary.config_kD(pidIdx, driveD, timeoutMs);
        Motors.rightDrivePrimary.config_kD(pidIdx, driveD, timeoutMs);
        
        Motors.leftDrivePrimary.config_kF(pidIdx, driveF, timeoutMs);
        Motors.rightDrivePrimary.config_kF(pidIdx, driveF, timeoutMs);
        
        // where do you plug in the error for the falcons
        Motors.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Motors.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
                
        Motors.leftDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        Motors.rightDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
                
        if (driveInches > 30) {
            Motors.leftDrivePrimary.configMotionCruiseVelocity(10100, timeoutMs);
            Motors.leftDrivePrimary.configMotionAcceleration(20000, timeoutMs);
            Motors.rightDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs);
            Motors.rightDrivePrimary.configMotionAcceleration(20000, timeoutMs);
        } else if (driveInches <= 30) {
            Motors.leftDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
            Motors.leftDrivePrimary.configMotionAcceleration(15000, timeoutMs);
            Motors.rightDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
            Motors.rightDrivePrimary.configMotionAcceleration(15000, timeoutMs);
        }
        Subsystems.Drive.changeControlMode(ControlMode.MotionMagic);
        Subsystems.Drive.setPID(-driveTicks, -driveTicks);
    }
    
    @Override
    public void execute() {
    }
    
    @Override
    public boolean isFinished() {
        double leftPosition = Subsystems.Drive.getLeftPosition();
        double rightPosition = Subsystems.Drive.getRightPosition();

        SmartDashboard.putNumber("left position in command", Subsystems.Drive.getLeftPosition());
        SmartDashboard.putNumber("right position in command", Subsystems.Drive.getRightPosition());

        driveGoal = -(driveTicks/13.85);

        SmartDashboard.putNumber("PID DRIVE TICKS: ", driveGoal);
        
        if ((leftPosition > (driveGoal - STOP_THRESHOLD_ADJUSTED) && leftPosition < (driveGoal + STOP_THRESHOLD_ADJUSTED)) ||
            (rightPosition > (driveGoal - STOP_THRESHOLD_ADJUSTED) && rightPosition < (driveGoal + STOP_THRESHOLD_ADJUSTED))) {
            onTargetCount++;
        } else {
            onTargetCount = 0;

        }
        SmartDashboard.putNumber("ONTARGETCOUNT", onTargetCount);
        return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
    }
    
    @Override
    public void end(boolean interrupted) {
        OI.distance = Math.abs(Subsystems.Drive.averageInchesDriven());
        Subsystems.Drive.enableVBusControl();
        Subsystems.Drive.resetBothEncoders();
        Subsystems.Drive.stop();
    }

}
