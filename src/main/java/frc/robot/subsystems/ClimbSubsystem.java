package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.ClimbDoNothing;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ClimbSubsystem extends SubsystemBase {
  private static final int pidIdx = 0;
  private static final int timeoutMs = 10;

  public TalonSRX latch = new TalonSRX(5);

  public CANSparkMax leftClimbPrimary = new CANSparkMax(12, MotorType.kBrushless);
  public CANSparkMax leftClimbSecondary = new CANSparkMax(9, MotorType.kBrushless);
  public CANSparkMax rightClimbPrimary = new CANSparkMax(3, MotorType.kBrushless);
  public CANSparkMax rightClimbSecondary = new CANSparkMax(8, MotorType.kBrushless);

  public ClimbSubsystem() {
    super();

    leftClimbPrimary.restoreFactoryDefaults();
    leftClimbPrimary.setOpenLoopRampRate(0.0);
    leftClimbPrimary.setSmartCurrentLimit(45);
    leftClimbPrimary.setInverted(false);

    leftClimbSecondary.restoreFactoryDefaults();
    leftClimbSecondary.setOpenLoopRampRate(0.0);
    leftClimbSecondary.setSmartCurrentLimit(45);
    leftClimbSecondary.setInverted(false);


    rightClimbPrimary.restoreFactoryDefaults();
    rightClimbPrimary.setOpenLoopRampRate(0.0);
    rightClimbPrimary.setSmartCurrentLimit(45);
    rightClimbPrimary.setInverted(true);

    rightClimbSecondary.restoreFactoryDefaults();
    rightClimbSecondary.setOpenLoopRampRate(0.0);
    rightClimbSecondary.setSmartCurrentLimit(45);
    rightClimbSecondary.setInverted(true);

    latch.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeoutMs);
    latch.setInverted(true);
    latch.setSensorPhase(true);
  }

  public void setClimb(double speed) {
    leftClimbPrimary.set(speed);
    leftClimbSecondary.set(speed);
    rightClimbPrimary.set(speed);
    rightClimbSecondary.set(speed);
  }

  public void setLeftClimb(double speed) {
    leftClimbPrimary.set(speed);
    leftClimbSecondary.set(speed);
  }

  public void setRightClimb(double speed) {
    rightClimbPrimary.set(speed);
    rightClimbSecondary.set(speed);
  }

  public void resetLatchEncoder() {
    latch.setSelectedSensorPosition(0);
  }

  public void changeClimbBrakeMode(boolean enabled) {
    if (enabled) {
      leftClimbPrimary.setIdleMode(IdleMode.kBrake);
      leftClimbSecondary.setIdleMode(IdleMode.kBrake);
      rightClimbPrimary.setIdleMode(IdleMode.kBrake);
      rightClimbSecondary.setIdleMode(IdleMode.kBrake);
    } else {
      leftClimbPrimary.setIdleMode(IdleMode.kCoast);
      leftClimbSecondary.setIdleMode(IdleMode.kCoast);
      rightClimbPrimary.setIdleMode(IdleMode.kCoast);
      rightClimbSecondary.setIdleMode(IdleMode.kCoast);
    }
  }

}
