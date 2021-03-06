package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Utilities.Limelight;
import frc.robot.Utilities.NavX;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  // private RobotContainer m_robotContainer;
  public static double distanceDone = 0; 
  SendableChooser<SequentialCommandGroup> autoChooser;
  @Override public void robotInit() {
    Limelight.getInstance().turnOff();
    new OI();
    new Constants();
  }
  @Override public void robotPeriodic() { CommandScheduler.getInstance().run(); }
  @Override public void disabledInit() { Drivetrain.getInstance().disable(); }
  @Override public void disabledPeriodic() {}
  @Override public void autonomousInit() {
    CommandScheduler.getInstance().cancelAll();
    Drivetrain.getInstance().setLeftReverse(false);
    NavX.getInstance().zeroAngle();
    Drivetrain.getInstance().zero();
    Limelight.getInstance().turnOff();
    //new BounceAuto().schedule();
    //Autonomous.Auto();
  }
  @Override public void autonomousPeriodic() {}
  @Override public void teleopInit() {
    CommandScheduler.getInstance().cancelAll();
    Drivetrain.getInstance().setLeftReverse(false);
    NavX.getInstance().zeroAngle();
  }
  @Override public void teleopPeriodic() {
    Drivetrain.getInstance().setSpeed(OI.LeftY() * .5, -OI.RightX() * .5);
    //SmartDashboard.putNumber("DistanceL", Drivetrain.getInstance().getLeftDistance());
    //SmartDashboard.putNumber("DistanceR", Drivetrain.getInstance().getRightDistance()); 
    SmartDashboard.putNumber("NavX: ", NavX.getInstance().Angle());
  }
  
  public static void MoveDistance(double angle, double speed, double distance) { 
		
		double distanceL = Drivetrain.getInstance().getLeftDistance();
		double distanceR = Drivetrain.getInstance().getRightDistance();
		double avgDistance = 0; 
		double distanceDone = (distanceL+distanceR)/2;
		double templ = speed; 
		double tempr = speed; 
		
		while(avgDistance < distance ) {
			SmartDashboard.putNumber("Gyro: ", NavX.getInstance().Angle());
			distanceL = Drivetrain.getInstance().getLeftDistance();
			distanceR = Drivetrain.getInstance().getRightDistance();
			SmartDashboard.putNumber("DistanceL: ", distanceL);
			SmartDashboard.putNumber("DistanceR: ", distanceR);
      Drivetrain.getInstance().setRaw(templ, tempr);
      /*
			if(Math.sin(Math.toRadians(NavX.getInstance().Angle()+angle)) < -.01) {
				if(templ < speed + .05) { templ += .001;  }
				else { templ -= .001; tempr -= .002; }
			}else if(Math.sin(Math.toRadians(NavX.getInstance().Angle()+angle)) > .01 ) {
				if(tempr < speed + .05) { tempr += .001; }
				else { tempr -= .001; templ -= .002; } 
			}else {
				templ = speed; 
				tempr = speed; 
      }
      */
			try{Thread.sleep(20);}catch(InterruptedException e){e.printStackTrace();}
			avgDistance = ((Drivetrain.getInstance().getLeftDistance()) + (Drivetrain.getInstance().getRightDistance())/2)-distanceDone;
			System.out.println(avgDistance); 
			
		}  
		Drivetrain.getInstance().setRaw(0, 0); 
  }
  public static void MoveForward(){
    MoveDistance(NavX.getInstance().Angle(), .25, 1);
  }
  
  
  @Override public void testInit() { CommandScheduler.getInstance().cancelAll(); }
  @Override public void testPeriodic() {}
}

