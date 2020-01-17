package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Robot;

public class PIDDriveInches extends CommandBase {
    private double driveTicks;
    private double driveInches;
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;

    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;

    private final static double STOP_THRESHOLD_REAL = 1;
    private static double STOP_THRESHOLD_ADJUSTED = Robot.m_driveSubsystem.applyGearRatio(Robot.m_driveSubsystem.convertInchesToTicks(STOP_THRESHOLD_REAL));
    
    private final static int allowableCloseLoopError = 1;
    
    private int onTargetCount = 0;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * .1;

    private double driveP = .5;
    
    private double driveI = 0;
    
    private double driveD = 0;
    
    private double driveF = 0;//.199;
    
    public PIDDriveInches(double inches, boolean reverse) {
        addRequirements(Robot.m_driveSubsystem);
        STOP_THRESHOLD_ADJUSTED*=Math.tanh(0.0625*inches); // added due to experimentation proving threshold should change over time
        SmartDashboard.putNumber("THRESHOLD", STOP_THRESHOLD_ADJUSTED);
        if (reverse) {
            this.driveTicks = -Robot.m_driveSubsystem.applyGearRatio(Robot.m_driveSubsystem.convertInchesToTicks(inches));//input now has to be ticks instead of revolutions which is why we multiply by 4096
        } else {
            this.driveTicks = Robot.m_driveSubsystem.applyGearRatio(Robot.m_driveSubsystem.convertInchesToTicks(inches));
        }
        
        this.driveInches = inches;
    }
    
    @Override
    public void initialize() {
        
        onTargetCount = 0;
        
        Robot.m_driveSubsystem.resetBothEncoders();

        //Robot.DRIVE_SUBSYSTEM.enablePositionControl();
        
        Robot.m_driveSubsystem.leftDrivePrimary.config_kP(pidIdx, driveP, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.config_kP(pidIdx, driveP, timeoutMs);
        
        Robot.m_driveSubsystem.leftDrivePrimary.config_kI(pidIdx, driveI, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.config_kI(pidIdx, driveI, timeoutMs);
        
        Robot.m_driveSubsystem.leftDrivePrimary.config_kD(pidIdx, driveD, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.config_kD(pidIdx, driveD, timeoutMs);
        
        Robot.m_driveSubsystem.leftDrivePrimary.config_kF(pidIdx, driveF, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.config_kF(pidIdx, driveF, timeoutMs);
        
        // where do you plug in the error for the falcons
        Robot.m_driveSubsystem.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.m_driveSubsystem.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.m_driveSubsystem.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.m_driveSubsystem.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
                
        Robot.m_driveSubsystem.leftDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
        Robot.m_driveSubsystem.rightDrivePrimary.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
                
        if (driveInches > 30) {
            Robot.m_driveSubsystem.leftDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs);
            Robot.m_driveSubsystem.leftDrivePrimary.configMotionAcceleration(20000, timeoutMs);
            Robot.m_driveSubsystem.rightDrivePrimary.configMotionCruiseVelocity(10000, timeoutMs);
            Robot.m_driveSubsystem.rightDrivePrimary.configMotionAcceleration(20000, timeoutMs);
        } else if (driveInches <= 30) {
            Robot.m_driveSubsystem.leftDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
            Robot.m_driveSubsystem.rightDrivePrimary.configMotionCruiseVelocity(7500, timeoutMs);
            Robot.m_driveSubsystem.rightDrivePrimary.configMotionAcceleration(15000, timeoutMs);
            Robot.m_driveSubsystem.rightDrivePrimary.configMotionAcceleration(15000, timeoutMs);
        }
        Robot.m_driveSubsystem.changeControlMode(ControlMode.MotionMagic);
        Robot.m_driveSubsystem.setPID(driveTicks, driveTicks);
    }
    
    @Override
    public void execute() {
        Robot.m_driveSubsystem.periodic();
    }
    
    @Override
    public boolean isFinished() {
        double leftPosition = Robot.m_driveSubsystem.getLeftPosition();
        double rightPosition = Robot.m_driveSubsystem.getRightPosition();

        SmartDashboard.putNumber("left position in command", Robot.m_driveSubsystem.getLeftPosition());
        SmartDashboard.putNumber("right position in command", Robot.m_driveSubsystem.getRightPosition());

        double driveGoal = driveTicks/13;

        SmartDashboard.putNumber("DRIVE TICKS: ", driveTicks);

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
        RobotContainer.distance = Math.abs(Robot.m_driveSubsystem.averageInchesDriven());
        Robot.m_driveSubsystem.enableVBusControl();
        Robot.m_driveSubsystem.resetBothEncoders();
        Robot.m_driveSubsystem.stop();
    }

}
