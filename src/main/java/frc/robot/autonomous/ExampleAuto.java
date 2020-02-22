// package frc.robot.autonomous;

// import frc.robot.Robot;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.commands.PIDDriveRotateCustom;

// public class ExampleAuto extends SequentialCommandGroup {

//   @Override
//   public void initialize() {
//     Robot.autoSideLeft = false;
//     Robot.autoSideRight = true;
//     Robot.autoSideFaceCargoShip = false;
//     Robot.autoFrontFaceCargoShip = false;
//     Robot.autoRocket = true;

//     Robot.operatorControl = false;
//   }

//    public ExampleAuto() {
//     addCommands(
//       new PIDDriveRotateCustom(45, false).withTimeout(4),
//       new PIDDriveRotateCustom(45, false).withTimeout(4)
//     );
//   }

//   @Override
//   public void end(boolean interrupted) {
//     Robot.operatorControl = true;
//   }
// }
