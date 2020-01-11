package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Robot;

public class PIDDriveInches extends CommandBase {
    private double driveTicks;
    private double driveInches;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;
    
    private final static double ENCODER_TICKS_PER_REVOLUTION = 2048;

    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;

    private final static double STOP_THRESHOLD_REAL = 1;
    private final static double STOP_THRESHOLD_ADJUSTED = Robot.DRIVE_SUBSYSTEM.convertInchesToTicks(STOP_THRESHOLD_REAL);
    
    private final static int allowableCloseLoopError = 1;
    
    private int onTargetCount = 0;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .1;

    private double driveP = .15;
    
    private double driveI = 0;
    
    private double driveD = 0;
    
    private double driveF = 0;//.199;
    
    public PIDDriveInches(double inches, boolean reverse) {
        addRequirements(Robot.DRIVE_SUBSYSTEM);
        SmartDashboard.putNumber("THRESHOLD", STOP_THRESHOLD_ADJUSTED);
        if (reverse) {
            this.driveTicks = -Robot.DRIVE_SUBSYSTEM.convertInchesToTicks(inches);//input now has to be ticks instead of revolutions which is why we multiply by 4096
        } else {
            this.driveTicks = Robot.DRIVE_SUBSYSTEM.convertInchesToTicks(inches);
        }
        
        this.driveInches = inches;
    }
    
    @Override
    public void initialize() {
        
        onTargetCount = 0;
        
        Robot.DRIVE_SUBSYSTEM.resetBothEncoders();

        //Robot.DRIVE_SUBSYSTEM.enablePositionControl();
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kP(pidIdx, driveP, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kP(pidIdx, driveP, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kI(pidIdx, driveI, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kI(pidIdx, driveI, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kD(pidIdx, driveD, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kD(pidIdx, driveD, timeoutMs);
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.config_kF(pidIdx, driveF, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.config_kF(pidIdx, driveF, timeoutMs);
        
        // where do you plug in the error for the falcons
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
                
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
                
        if (driveInches > 30) {
            Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs);
            Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionAcceleration(20000, timeoutMs);
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs);
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionAcceleration(20000, timeoutMs);
        } else if (driveInches <= 30) {
            Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configMotionCruiseVelocity(7500
            , timeoutMs);
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionAcceleration(15000, timeoutMs);
            Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configMotionAcceleration(15000, timeoutMs);
        }
        Robot.DRIVE_SUBSYSTEM.changeControlMode(ControlMode.MotionMagic);
        Robot.DRIVE_SUBSYSTEM.setPID(driveTicks, driveTicks);
    }
    
    @Override
    public void execute() {
    }
    
    @Override
    public boolean isFinished() {
        double leftPosition = Robot.DRIVE_SUBSYSTEM.getLeftPosition();
        double rightPosition = Robot.DRIVE_SUBSYSTEM.getRightPosition();

        SmartDashboard.putNumber("left position in command", Robot.DRIVE_SUBSYSTEM.getLeftPosition());
        SmartDashboard.putNumber("right position in command", Robot.DRIVE_SUBSYSTEM.getRightPosition());


        SmartDashboard.putNumber("DRIVE TICKS: ", driveTicks);

        if ((leftPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && leftPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED)) ||
            (rightPosition > (driveTicks - STOP_THRESHOLD_ADJUSTED) && rightPosition < (driveTicks + STOP_THRESHOLD_ADJUSTED))) {
            onTargetCount++;
        } else {
            onTargetCount = 0;

        }
        SmartDashboard.putNumber("ONTARGETCOUNT", onTargetCount);
        return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
    }
    
    @Override
    public void end(boolean interrupted) {
        OI.distance = Math.abs(Robot.DRIVE_SUBSYSTEM.averageInchesDriven());
        Robot.DRIVE_SUBSYSTEM.enableVBusControl();
        Robot.DRIVE_SUBSYSTEM.resetBothEncoders();
        Robot.DRIVE_SUBSYSTEM.stop();
    }

}
