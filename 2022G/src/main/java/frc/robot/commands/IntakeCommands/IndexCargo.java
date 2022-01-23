package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.utils.Constants;
import frc.robot.utils.RobotMap;

public class IndexCargo extends CommandBase{
    private Hopper hopper;
    private Intake intake;
    private Flywheel flywheel;

    public IndexCargo() {
        hopper = Hopper.getInstance();
        intake = Intake.getInstance();
        flywheel = Flywheel.getInstance();

        addRequirements(hopper);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute() {
        // Rewrite indexing algorithm.
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      hopper.stopHopper();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
    
  }