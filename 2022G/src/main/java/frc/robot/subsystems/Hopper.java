package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.*;
import frc.robot.utils.RobotMapGullinkambi;

public class Hopper extends SubsystemBase {
    private static Hopper hopper;
    private LinearFilter filter;

    private CANSparkMax hopperSystem;

    // Define sensors for the hopper to count cargo
    private DigitalInput bottomSensor, topSensor;

    private static UpdateLogs updateLogs = UpdateLogs.getInstance();

    public Hopper() {
        hopperSystem = new CANSparkMax(RobotMapGullinkambi.MOTOR_HOPPER, MotorType.kBrushless);
        hopperSystem.setIdleMode(IdleMode.kBrake);
        hopperSystem.setSmartCurrentLimit(Constants.HOPPER_MAX_CURRENT);
        filter = LinearFilter.singlePoleIIR(0.2, 0.02);

        bottomSensor = new DigitalInput(1);
        topSensor = new DigitalInput(0);
    }

    public void periodic() {
        updateLogs.updateHopperLogData();
    }

    public static Hopper getInstance() {
        if (hopper == null) {
            hopper = new Hopper();
        }

        return hopper;
    }

    public void runHopper(double speed){
        hopperSystem.set(-speed); //the speed input needs a multiplier
    }

    public void stopHopper() {
        runHopper(0);
    }

    public void reverseHopper(double speed) {
        runHopper(-speed);
    }

    //Getters
    public double getHopperVelocity(){
        return hopperSystem.get();
    }

    public double getHopperCurrent(){
        return hopperSystem.getOutputCurrent();
    }

    public double getHopperMotorTemperature(){
        return hopperSystem.getMotorTemperature();
    }

    public double getHopperEncoderVelocity(){
        return hopperSystem.getEncoder().getVelocity();
    }

    public double getHopperEncoderPosition(){
        return hopperSystem.getEncoder().getPosition();
    }

    public boolean sensesBallBottom() {
        boolean filteredInput = false;
        if(!bottomSensor.get()){
            double x = filter.calculate(1);
            filteredInput = x > Constants.LOWER_SENSOR_INPUT_THRESHOLD;
        } else {
            double x = filter.calculate(0);
            filteredInput = x > Constants.LOWER_SENSOR_INPUT_THRESHOLD;
        }
        SmartDashboard.putBoolean("filteredInput", filteredInput);
        return filteredInput;
    }

    public boolean sensesBallTop() {
        return !topSensor.get();
    }

    public void putSmartDashboardOverrides() {
        SmartDashboard.putNumber("OR: Hopper speed", 0.0);
    }

    public void updateHopperInfoOnDashboard(){
        SmartDashboard.putNumber("Hopper speed", getHopperVelocity());
        SmartDashboard.putBoolean("Lower sensor", sensesBallBottom());
        SmartDashboard.putBoolean("Upper sensor", sensesBallTop());
    }

    public void updateHopperFromDashboard() {
        runHopper(SmartDashboard.getNumber("OR: Hopper speed", Constants.HOPPER_SPEED));
    }
}
