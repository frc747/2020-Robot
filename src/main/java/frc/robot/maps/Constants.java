package frc.robot.maps;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * Drive Distances are in inches All lift and climb positions are in ticks
 * Rotations are in degrees Times are in seconds Speed values are in motor %
 */
 
public class Constants {
    private Constants() {
    }

    public final class Measurements{
        
        private Measurements(){
        }
        
        public static final double ROBOT_LENGTH = 38.5; // CHANGE THESE
        public static final double ROBOT_WIDTH = 34; // CHANGE THESE
    }
    
    public static enum Controller {
        LEFT_STICK(0),
        RIGHT_STICK(1),
        OPERATOR_CONTROLLER(2),
        TEST_CONTROLLER(3);

        private int value;

        private Controller(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    public static final double ksVolts = 0.345;
    public static final double kvVoltSecondsPerMeter = 0.0766;
    public static final double kaVoltSecondsSquaredPerMeter = 0.00697;

    public static final double kP = 0.659;
    public static final double kD = 0.0;

    public static final double kTrackWidthMeters = 0.5969;
    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackWidthMeters);

    public static final double kMaxSpeedMetersPerSecond = 3.5;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

}
