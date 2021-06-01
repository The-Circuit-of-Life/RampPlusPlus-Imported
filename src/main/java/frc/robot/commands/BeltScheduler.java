package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.Belt;

public class BeltScheduler extends CommandBase {
  public BeltScheduler() {}
  @Override public void initialize() {
    Belt.getInstance().setBeltInverts(false, true);
    Belt.getInstance().setRawSpeed(0.1, Constants.BELT_SPEED);
  }
  @Override public void execute() {}
  @Override public void end(boolean interrupted) {Belt.getInstance().setRawSpeed(0, 0);}
  @Override public boolean isFinished() {return !OI.intake.get() || !Belt.getInstance().getLimitSwitch();}
}