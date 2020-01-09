// package frc.robot.commands;

// import com.ctre.phoenix.motorcontrol.ControlMode;

// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.OI;
// import frc.robot.Robot;

// public class DartDriveCommand extends CommandBase {
//   public DartDriveCommand() {
//     addRequirements(Robot.ACTUATOR_SUBSYSTEM);
//   }

//   // Called just before this Command runs the first time
//   @Override
//   public void initialize() {
//   }

//   // Called repeatedly when this Command is scheduled to run
//   @Override
//   public void execute() {
//     double power = OI.testController.getRawAxis(1);

//     if(!OI.testController.getRawButton(6)) {
//       power = 0;
//     }

//     if(power < .05 && power > -.05) {
//       power = 0;
//     }

//     //

//     Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, power);
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   public boolean isFinished() {
//     return false;
//   }

//   // Called once after isFinished returns true
//   @Override
//   public void end(boolean interrupted) {
//     Robot.ACTUATOR_SUBSYSTEM.dartTalon.set(ControlMode.PercentOutput, 0);
//   }
// }
