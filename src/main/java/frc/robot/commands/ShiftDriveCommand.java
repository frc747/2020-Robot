// package frc.robot.commands;

// import com.ctre.phoenix.motorcontrol.ControlMode;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.CommandBase;

// import frc.robot.Motors;
// import frc.robot.OI;
// import frc.robot.Robot;
// import frc.robot.Subsystems;
// import frc.robot.input.Devices;


// public class ShiftDriveCommand extends CommandBase {
        
//     private double driveTicks = 830;
    
//     private static final int pidIdx = 0;
//     private static final int timeoutMs = 10;
//     private static final int slotIdx = 0;
//     private int shiftCount = 0;

//     private static final double MAX_PERCENT_VOLTAGE = 1.0;
//     private static final double MIN_PERCENT_VOLTAGE = 0.0;
    
//     private final static int allowableCloseLoopError = 1;
    
//     private double driveShiftP = 1.0;
    
//     private double driveShiftI = 0;
    
//     private double driveShiftD = 0;

//     private double driveShiftF = 1.5;
    
//     private double leftValue;

//     private double rightValue;

//     private double rotateValue;

//     // private double shifterValue;

//     public ShiftDriveCommand() {
//         addRequirements(Subsystems.Drive);
//     }
    
//     @Override
//     public void initialize() {
//         SmartDashboard.putBoolean("Currently Vision Tracking", false);
        
//         Subsystems.Drive.tracking = false;

//         Motors.leftDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
//         Motors.leftDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
//         Motors.leftDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
//         Motors.leftDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
//         Motors.rightDrivePrimary.configNominalOutputForward(+MIN_PERCENT_VOLTAGE, timeoutMs);
//         Motors.rightDrivePrimary.configNominalOutputReverse(-MIN_PERCENT_VOLTAGE, timeoutMs);
//         Motors.rightDrivePrimary.configPeakOutputForward(+MAX_PERCENT_VOLTAGE, timeoutMs);
//         Motors.rightDrivePrimary.configPeakOutputReverse(-MAX_PERCENT_VOLTAGE, timeoutMs);
    
//     }
    
//     @Override
//     public void execute() {
//         // manual application of a sloppy "deadband"

//         // leftValue = -OI.leftStick.getRawAxis(1); // before modifying raw of axis 1: forward = negative, backward = positive
//         // rightValue = -OI.rightStick.getRawAxis(1);
//         // rotateValue = OI.rightStick.getRawAxis(3); // before modifying raw of axis 3: CCW = negative, CW = positive
//         // // shifterValue = OI.operatorController.getRawAxis(5);

//         // if (Math.abs(leftValue) < 0.1) {
//         //     leftValue = 0;
//         // }
//         // if (Math.abs(rightValue) < 0.1) {
//         //     rightValue = 0;
//         // }
        
//         // cleaned up version of a deadband, helper method at bottom of the class

//         leftValue = applyDeadband(-Devices.leftStick.getY(), 0.1); // before modifying raw of axis 1: forward = negative, backward = positive
//         rightValue = applyDeadband(-Devices.rightStick.getY(), 0.1);
//         rotateValue = applyDeadband(Devices.rightStick.getZ(), 0.1); // before modifying raw of axis 3: CCW = negative, CW = positive
        
//         if (Devices.leftStick.getBaseTopRight()) {
//             // drive straight function


//             // while holding button 8 on the left joystick, the value from the Y-Axis of the right joystick will be applied to both sides of the drive train so that it will drive in the same direction of the movement done by the joystick

//             double straightDrive = rightValue;

//             // when forward, left and right side both go forward
//             // when backward, left and right side both go backward
//             Subsystems.Drive.set(straightDrive, straightDrive);
//         } else if (Devices.leftStick.getBaseTopLeft()) {
//             // drive rotate function


//             // while holding button 7 on the left joystick, the value from the Z-Axis of the right joystick will be applied to the one side of the drive train and the negative of that value will be applied to the other side so that it will rotate in the same direction of the rotation done by the joystick

//             double rotateDrive = rotateValue * Math.sqrt(0.5);

//             rotateDrive = Math.copySign(rotateValue*rotateValue, rotateValue);
//             // when counter clockwise, left goes backward and right side goes forward
//             // when clockwise, left side goes forward and right side goes backward
//             Subsystems.Drive.set(rotateDrive, -rotateDrive);            
//         } else {
//             Subsystems.Drive.set(leftValue, rightValue);
//         }

//         // check if the robot should be considered moving towards high gear or stay in low gear
//         if((leftValue > .9 && rightValue > .9) || (leftValue < -.9 && rightValue < -.9) && (Motors.leftDrivePrimary.getSelectedSensorVelocity() > 1600 || Motors.leftDrivePrimary.getSelectedSensorVelocity() < -1600)) {
//             shiftCount++;
//         } else {
//             shiftCount = 0;
//         }
      
//         if (OI.operatorController.getRawAxis(2) > 0.25) {
//             Subsystems.Drive.tracking = true;
//         } else {
//             Subsystems.Drive.tracking = false;
//         }

//         // if shift count has been adding for half a second
//         if(shiftCount > 25) {
//             OI.shiftHigh = true;
//         } else {
//             OI.shiftHigh = false;
//         }
       
//         if (OI.shiftHigh && !(OI.operatorController.getRawAxis(3) > .25)) {
//             // Subsystems.Drive.gearShifter.set(ControlMode.PercentOutput, shifterValue);
//             if (Subsystems.Drive.gearShifter.getSelectedSensorPosition() > driveTicks - 10 && Subsystems.Drive.gearShifter.getSelectedSensorPosition() < driveTicks + 10) {
//                 Subsystems.Drive.gearShifter.set(ControlMode.PercentOutput, 0);
//             } else {
//                 Subsystems.Drive.gearShifter.configMotionCruiseVelocity(7500, 10); //1500
//                 Subsystems.Drive.gearShifter.configMotionAcceleration(20000, 10); //2000
//                 Subsystems.Drive.gearShifter.set(ControlMode.MotionMagic, driveTicks);
//             }

//         } else {
//             // Subsystems.Drive.gearShifter.set(ControlMode.PercentOutput, 0);
//             if (Subsystems.Drive.gearShifter.getSelectedSensorPosition() > -10 && Subsystems.Drive.gearShifter.getSelectedSensorPosition() < 10) {
//                 Subsystems.Drive.gearShifter.set(ControlMode.PercentOutput, 0);
//             } else {
//                 Subsystems.Drive.gearShifter.set(ControlMode.MotionMagic, 0);
//             }
//         }
//     }
    
//     @Override
//     public boolean isFinished() {
//          return false;
//     }
    
//     @Override
//     public void end(boolean interrupted) {
//         Subsystems.Drive.gearShifter.set(ControlMode.PercentOutput, 0);
//     }

//     private double applyDeadband(double value, double deadband) {
//         if (Math.abs(value) > deadband) {
//           if (value > 0.0) {
//             return (value - deadband) / (1.0 - deadband);
//           } else {
//             return (value + deadband) / (1.0 - deadband);
//           }
//         } else {
//           return 0.0;
//         }
//     }
      
// }