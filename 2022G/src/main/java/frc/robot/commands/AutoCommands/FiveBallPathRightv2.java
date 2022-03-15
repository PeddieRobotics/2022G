package frc.robot.commands.AutoCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveCommands.ResetOdometry;
import frc.robot.commands.IntakeCommands.AutoIntakeWithHopper;
import frc.robot.commands.ShootCommands.SetFlywheelRPM;

public class FiveBallPathRightv2 extends SequentialCommandGroup{ 

    public FiveBallPathRightv2(Pose2d initialPose, SplitFFRamseteCommand part1, SplitFFRamseteCommand part2, SplitFFRamseteCommand part3, SplitFFRamseteCommand part4){
        addCommands(
            new ResetOdometry(initialPose),
            new SetFlywheelRPM(2650),
            new ParallelCommandGroup(
                new AutoIntakeWithHopper(0.7, 0.7),
                part1
            ),
            new ShootWithLLUntilEmptyNoTarget(0.5),
            part2,
            new AutoIntakeWithHopper(0.7, 0.7),
            new ShootWithLLUntilEmptyNoTarget(0.5),
            part3,
            new WaitCommand(1),
            part4,
            new ShootWithLLForTime(5)
        );
    }
}
