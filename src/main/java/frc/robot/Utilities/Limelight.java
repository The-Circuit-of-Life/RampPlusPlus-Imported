package frc.robot.Utilities;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private NetworkTable table;
    private NetworkTableEntry tx, ty;
    private boolean isTracking;
    private static Limelight in;
    public static Limelight getInstance(){if(in==null)in=new Limelight();return in;}
    private Limelight(){
        table = NetworkTableInstance.getDefault().getTable("limelight");
        turnOff();
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
    }
    public void switchState(){if(isTracking){ turnOff();}else{turnOn();}}
    public double getX(){return tx.getDouble(0.0);}
    public double getY(){return ty.getDouble(0.0);}
    public void turnOn(){
        table.getEntry("ledMode").setNumber(3);
        table.getEntry("camMode").setNumber(0);
        table.getEntry("stream").setNumber(1);
        isTracking = true;
    }
    public void turnOff(){
        table.getEntry("ledMode").setNumber(1);
        table.getEntry("camMode").setNumber(1);
        table.getEntry("stream").setNumber(2);
        isTracking = false;
    }
}