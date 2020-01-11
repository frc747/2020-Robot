package frc.robot.commands;

import frc.robot.Robot;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpiutil.math.MathUtil;

public class PIDDriveRotate extends PIDCommand {

    private double angleToRotate;
    
    private int onTargetCount;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    // Multiply TARGET_COUNT_ONE_SECOND by the amount of time that you want for your minimum count threshold
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * 0.1;
    
    private final static double STOP_THRESHOLD_DEGREES = 5;//4.25
    private final static double MAX_PERCENT_VBUS = 1.0;
    
    private static final double MAX_PERCENT_VOLTAGE = 1.0;
    private static final double MIN_PERCENT_VOLTAGE = 0.10;
    
    private static final int timeoutMs = 10;
    
    private static DoubleSupplier currentAngle = () -> {return Robot.getPigeonAngle();};
    private static DoubleConsumer output = (x) -> {MathUtil.clamp(x, -MAX_PERCENT_VBUS, MAX_PERCENT_VBUS);};
    
    //constructor supports Closest Equivalent
    public PIDDriveRotate(double degreesRotate, boolean closestEquivalent) {
        super(new PIDController(0.03, 0.0, 0.05), currentAngle, degreesRotate, output, Robot.DRIVE_SUBSYSTEM);
        
        if (closestEquivalent)  {
    
            if (degreesRotate > 0) {
                this.angleToRotate = ((degreesRotate+180)%360)-180; //limits angle to range of -180 to 180
            } else {
                this.angleToRotate = ((degreesRotate-180)%360)+180; //limits angle to range of -180 to 180
            }
        } else {
            this.angleToRotate = degreesRotate;
        }

        addRequirements(Robot.DRIVE_SUBSYSTEM);
    }

    //Default constructor Exact Mode
    public PIDDriveRotate(double degreesRotate) {

        super(new PIDController(0.03, 0.0, 0.05), currentAngle, degreesRotate, output, Robot.DRIVE_SUBSYSTEM);

        this.angleToRotate = degreesRotate;

        addRequirements(Robot.DRIVE_SUBSYSTEM);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        
        Robot.resetPigeonAngle();
        //Robot.resetNavXAngle();
        
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
        Robot.DRIVE_SUBSYSTEM.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
        
        onTargetCount = 0;
        
        getController().setTolerance(STOP_THRESHOLD_DEGREES); //the threshold that the PID Controller abides by to consider the value as "on target"
        getController().enableContinuousInput(-200000, 200000);
        getController().setSetpoint(angleToRotate);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        double out = 0;
        output.accept(out);
        SmartDashboard.putNumber("rotation values", out);
        Robot.DRIVE_SUBSYSTEM.set(out, out);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        if (getController().atSetpoint()) {
            onTargetCount++;
        } else {
            onTargetCount = 0;
        }
                    
        return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {

    }
}
