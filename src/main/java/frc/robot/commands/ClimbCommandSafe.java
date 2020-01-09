package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Robot;

public class ClimbCommandSafe extends CommandBase {
        
    private double driveTicks = 8192; // FIND VALUE FOR PIN DISENGAGED
    
    private static final int pidIdx = 0;
    private static final int timeoutMs = 10;
    private static final int slotIdx = 0;

    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.0;
    
    private final static int allowableCloseLoopError = 1;
    
    private int LEFT_BUMPER = 5;
    private int RIGHT_BUMPER = 6;

    private double specificDistanceP = 1.0;

    private double specificDistanceI = 0;
    
    private double specificDistanceD = 0;

    private double specificDistanceF = 1.5;

    public ClimbCommandSafe() {
        addRequirements(Robot.CLIMB_SUBSYSTEM);
    }
    
    @Override 
    public void initialize() {
        OI.latchInPosition = false;
        Robot.CLIMB_SUBSYSTEM.latch.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.CLIMB_SUBSYSTEM.latch.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.CLIMB_SUBSYSTEM.latch.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.CLIMB_SUBSYSTEM.latch.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
        Robot.CLIMB_SUBSYSTEM.latch.configAllowableClosedloopError(slotIdx, allowableCloseLoopError, timeoutMs);
             
        Robot.CLIMB_SUBSYSTEM.latch.configMotionCruiseVelocity(1500, 10);
        Robot.CLIMB_SUBSYSTEM.latch.configMotionAcceleration(2000, 10);

        Robot.CLIMB_SUBSYSTEM.latch.config_kP(pidIdx, specificDistanceP, timeoutMs);
        
        Robot.CLIMB_SUBSYSTEM.latch.config_kI(pidIdx, specificDistanceI, timeoutMs);
        
        Robot.CLIMB_SUBSYSTEM.latch.config_kD(pidIdx, specificDistanceD, timeoutMs);
        
        Robot.CLIMB_SUBSYSTEM.latch.config_kF(pidIdx, specificDistanceF, timeoutMs);
    }
    
    @Override
    public void execute() {

        if (OI.operatorController.getRawButton(RIGHT_BUMPER) && !OI.latchInPosition) {
            if (Robot.CLIMB_SUBSYSTEM.latch.getSelectedSensorPosition() > driveTicks - 200 && Robot.CLIMB_SUBSYSTEM.latch.getSelectedSensorPosition() < driveTicks + 1000) {
                Robot.CLIMB_SUBSYSTEM.latch.set(ControlMode.PercentOutput, 0);

                OI.latchInPosition = true;
            } else {
                Robot.CLIMB_SUBSYSTEM.latch.config_kP(pidIdx, specificDistanceP, timeoutMs);
                Robot.CLIMB_SUBSYSTEM.latch.config_kI(pidIdx, specificDistanceI, timeoutMs);
                Robot.CLIMB_SUBSYSTEM.latch.config_kD(pidIdx, specificDistanceD, timeoutMs);
                Robot.CLIMB_SUBSYSTEM.latch.config_kF(pidIdx, specificDistanceF, timeoutMs);

                Robot.CLIMB_SUBSYSTEM.latch.set(ControlMode.MotionMagic, driveTicks);
            }
        }

        if(OI.operatorController.getRawButton(LEFT_BUMPER) && OI.latchInPosition) {
            double winchValue = Math.abs(OI.operatorController.getRawAxis(1));

            if (winchValue > 0.4) {
                winchValue = 0.4;
            }

            Robot.CLIMB_SUBSYSTEM.setClimb(winchValue);
        } else {
            Robot.CLIMB_SUBSYSTEM.setClimb(0.0);
        }
    }
    
    @Override
    public boolean isFinished() {
         return false;
    }
    
    @Override
    public void end(boolean interrupted) {
        Robot.CLIMB_SUBSYSTEM.latch.set(ControlMode.PercentOutput, 0);
    }
}